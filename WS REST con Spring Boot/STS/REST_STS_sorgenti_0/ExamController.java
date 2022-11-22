package tsdw.rest.esami.controller;

@RestController
@RequestMapping("/api")
public class ExamController {

	@Autowired
	private ExamService examService;

	@GetMapping("/exams")
	public ResponseEntity<List<Exam>> getAllExams() {
		List<Exam> exams = examService.getAllExams();
		return ResponseEntity.ok().body(exams);
	}

	@GetMapping("/exams/{id}")
	public ResponseEntity<Exam> getExam(@PathVariable Long id) {
		Optional<Exam> exam = examService.getExam(id);
		if (exam.isPresent())
			return ResponseEntity.ok().body(exam.get());
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/exams")
	public ResponseEntity<Exam> addExam(@Valid @RequestBody Exam e) throws URISyntaxException {
		if (e.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		Exam result = examService.addExam(e);
		return ResponseEntity.created(new URI("/api/exams/" + result.getId())).body(result);
	}

	@PutMapping("/exams")
	public ResponseEntity<Exam> updateExam(@Valid @RequestBody Exam e) {
		if (e.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		Exam result = examService.updateExam(e);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/exams/{id}")
	public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
		if (!examService.getExam(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		// Should delete all exam's results
		resultService.getExamResults(id).forEach(result -> resultService.deleteResult(result.getId()));

		examService.deleteExam(id);
		return ResponseEntity.ok().build();
	}

}
