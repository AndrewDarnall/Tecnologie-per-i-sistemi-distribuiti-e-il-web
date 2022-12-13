import java.lang.annotation.Inherited;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Students {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;
    private String degree_course;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDegreeCourse(String degree_course) {
        this.degree_course = degree_course;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public Integer getAge() {
        return age;
    }

    public String getDegreeCourse() {
        return degree_course;
    }

    // Not too much freedom with this method
    @Override
    public String toString() {
        return "Students{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}

/**
 * This is the class that will perform the mapping
 */