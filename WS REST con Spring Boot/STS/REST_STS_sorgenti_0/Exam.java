package tsdw.rest.esami.domain;

@Entity
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String description;
	@NotNull
	private Date date;

	public Exam() {
	}

	public Exam(String description, Date date) {
		this.description = description;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", description=" + description + ", date=" + date + "]";
	}

}
