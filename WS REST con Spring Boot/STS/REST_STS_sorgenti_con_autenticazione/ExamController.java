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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portal.domain.Exam;
import portal.service.ExamService;
import portal.service.ResultService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ExamController {
	private final static Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	private ExamService examService;

	@Autowired
	private ResultService resultService;

	@GetMapping("/exams")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Exam>> getAllExams() {
		log.debug("GET on /exams");
		List<Exam> exams = examService.getAllExams();
		return ResponseEntity.ok().body(exams);
	}

	@GetMapping("/exams/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Exam> getExam(@PathVariable Long id) {
		log.debug("GET on /exams/" + id.toString());
		Optional<Exam> exam = examService.getExam(id);
		if (exam.isPresent())
			return ResponseEntity.ok().body(exam.get());
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/exams")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Exam> addExam(@Valid @RequestBody Exam e) throws URISyntaxException {
		log.debug("POST on /exams with body: " + e.toString());
		if (e.getId() != null) {
			log.error("A new exam cannot already have an ID");
			return ResponseEntity.badRequest().build();
		}
		Exam result = examService.addExam(e);
		return ResponseEntity.created(new URI("/api/exams/" + result.getId())).body(result);
	}

	@PutMapping("/exams")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Exam> updateExam(@Valid @RequestBody Exam e) {
		log.debug("PUT on /exams/ with body: " + e.toString());
		if (e.getId() == null) {
			log.error("Invalid ID");
			return ResponseEntity.badRequest().build();
		}
		Exam result = examService.updateExam(e);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/exams/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
		log.debug("DELETE on /exams/" + id.toString());
		if (!examService.getExam(id).isPresent()) {
			log.error("Not found");
			return ResponseEntity.notFound().build();
		}

		// First delete all exam's results
		resultService.getExamResults(id).forEach(result -> resultService.deleteResult(result.getId()));

		examService.deleteExam(id);
		return ResponseEntity.ok().build();
	}

}
