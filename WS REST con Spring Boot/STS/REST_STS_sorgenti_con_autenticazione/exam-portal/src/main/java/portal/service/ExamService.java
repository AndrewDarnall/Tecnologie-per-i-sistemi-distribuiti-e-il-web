package portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.domain.Exam;
import portal.repository.ExamRepository;

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
