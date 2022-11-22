package portal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.domain.Result;
import portal.repository.ResultRepository;

@Service
public class ResultService {

	@Autowired
	private ResultRepository resultRepository;

	public Result addResult(Result r) {
		return resultRepository.save(r);
	}

	public List<Result> getAllResults() {
		List<Result> results = new ArrayList<>();
		resultRepository.findAll().forEach(results::add);
		return results;
	}

	public List<Result> getExamResults(Long examId) {
		List<Result> results = new ArrayList<>();
		resultRepository.findByExamId(examId).forEach(results::add);
		return results;
	}

	public List<Result> getStudentResults(String student) {
		List<Result> results = new ArrayList<>();
		resultRepository.findByStudent(student).forEach(results::add);
		return results;
	}

	public Optional<Result> getResult(Long id) {
		return resultRepository.findById(id);
	}

	public Result updateResult(Result r) {
		return resultRepository.save(r);
	}

	public void deleteResult(Long id) {
		resultRepository.deleteById(id);
	}

}
