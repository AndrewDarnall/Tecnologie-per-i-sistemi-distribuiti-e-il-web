package portal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "result", uniqueConstraints = { @UniqueConstraint(columnNames = { "student", "exam_id" }) })
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@NotNull
	private Exam exam;

	@NotBlank
	@Size(max = 16)
	private String student;

	@NotBlank
	private String mark;

	private String note;

	public Result() {
	}

	public Result(Exam exam, String student, String mark, String note) {
		this.exam = exam;
		this.student = student;
		this.mark = mark;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", exam=" + exam + ", student=" + student + ", mark=" + mark + ", note=" + note
				+ "]";
	}

}
