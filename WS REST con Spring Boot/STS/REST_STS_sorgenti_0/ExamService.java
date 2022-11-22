package tsdw.rest.esami.service;

@Service
public class ExamService {

	@Autowired
	private ExamRepository examRepository;

	public Exam addExam(Exam e) {
		return examRepository.save(e);
	}

	public List<Exam> getAllExams() {
		List<Exam> exams = new ArrayList<>();
		examRepository.findAll().forEach(exams::add);
		return exams;
	}

	public Optional<Exam> getExam(Long id) {
		return examRepository.findById(id);
	}

	public Exam updateExam(Exam e) {
		return examRepository.save(e);
	}

	public void deleteExam(Long id) {
		examRepository.deleteById(id);
	}

}
