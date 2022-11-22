package portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portal.domain.Result;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {
	List<Result> findByExamId(Long id);

	List<Result> findByStudent(String student);
}
