package hac.ex5.course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

import hac.ex5.student.Student;

/**
 * Course class represent the Course that have students and teacher.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course implements Serializable {
    private static final long serialVersionUID = 277L;

    /**
     * course id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * course name
     */
    @Size(min = 2, max = 40, message  = "Course name should be between 2 and 40 letters.")
    @NotEmpty(message = "Course name is mandatory and unique.")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**
     * teacher name
     */
    @Size(min = 2, max = 20, message  = "Lecturer name should be between 2 and 20 letters.")
    @NotNull(message = "Lecturer name is mandatory.")
    @NotEmpty(message = "Lecturer name is mandatory.")
    private String lecturerUserName;

    /**
     * course descriptions
     */
    private String descriptions = "";

    /**
     * forum data
     */
    @ElementCollection
    private List<String> forum = new ArrayList<>();

    /**
     * student enrolled to the course
     */
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    /**
     * check if two course are equals
     * @param o course
     * @return boolean two course are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    /**
     * hash course
     * @return the hashed id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}