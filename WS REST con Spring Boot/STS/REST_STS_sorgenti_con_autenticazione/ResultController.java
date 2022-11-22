package portal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portal.domain.Result;
import portal.service.ExamService;
import portal.service.ResultService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ResultController {
	private static final Logger log = LoggerFactory.getLogger(ResultController.class);

	@Autowired
	private ResultService resultService;

	@Autowired
	private ExamService examService;

	@GetMapping("/results")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Result>> getAllResults() {
		log.debug("GET on /results");
		List<Result> results = resultService.getAllResults();
		return ResponseEntity.ok().body(results);
	}

	@GetMapping("/exams/{exam}/results")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Result>> getAllResults(@PathVariable Long exam) {
		log.debug("GET on /exams/" + exam.toString() + "/results");
		List<Result> results = resultService.getExamResults(exam);
		return ResponseEntity.ok().body(results);
	}

	@GetMapping("/user/results")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Result>> getStudentResults() {
		log.debug("GET on /user/results");
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Result> results = resultService.getStudentResults(user);
		return ResponseEntity.ok().body(results);
	}

	@GetMapping("/user/{student}/results")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Result>> getAllStudentResults(@PathVariable String student) {
		log.debug("GET on /user/" + student + "/results");
		List<Result> results = resultService.getStudentResults(student);
		return ResponseEntity.ok().body(results);
	}

	@GetMapping("/results/{result}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Result> getResult(@PathVariable Long result) {
		log.debug("GET on /results/" + result.toString());
		Optional<Result> res = resultService.getResult(result);
		if (res.isPresent())
			return ResponseEntity.ok().body(res.get());
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/results")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Result> addResult(@Valid @RequestBody Result r) throws URISyntaxException {
		log.debug("POST on /results with body: " + r.toString());
		if (r.getId() != null) {
			log.error("A new result cannot already have an ID");
			return ResponseEntity.badRequest().build();
		}
		if (!examService.getExam(r.getExam().getId()).isPresent()) {
			log.error("Exam not found");
			return ResponseEntity.notFound().build();
		}
		Result res = resultService.addResult(r);
		return ResponseEntity.created(new URI("/api/results/" + res.getId())).body(res);
	}

	@PutMapping("/results")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Result> updateResult(@Valid @RequestBody Result r) {
		log.debug("PUT on /results with body: " + r.toString());
		if (r.getId() == null) {
			log.error("Invalid ID");
			return ResponseEntity.badRequest().build();
		}
		if (!examService.getExam(r.getExam().getId()).isPresent()) {
			log.error("Exam not found");
			return ResponseEntity.notFound().build();
		}
		Result res = resultService.updateResult(r);
		return ResponseEntity.ok().body(res);
	}

	@DeleteMapping("/results/{result}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteResult(@PathVariable Long result) {
		log.debug("DELETE on /results/" + result.toString());
		if (!resultService.getResult(result).isPresent()) {
			log.error("Not found");
			return ResponseEntity.notFound().build();
		}
		resultService.deleteResult(result);
		return ResponseEntity.ok().build();
	}
}
