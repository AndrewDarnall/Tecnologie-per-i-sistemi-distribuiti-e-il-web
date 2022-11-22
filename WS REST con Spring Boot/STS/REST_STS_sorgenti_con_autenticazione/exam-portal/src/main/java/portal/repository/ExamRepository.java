package portal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import portal.domain.Exam;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {

}
