package hac.ex5.student;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import hac.ex5.course.*;

/**
 * Student class represent the Student that can see courses data and enroll.
 */
@Entity
@Getter
@Setter
public class Student implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * Student id
     */
    @Positive
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    /**
     * Student userName
     */
    @Size(min = 2, max = 20, message  = "Student name should be between 2 and 20 letters.")
    @NotNull(message = "Student name is mandatory.")
    @NotEmpty(message = "Student name is mandatory.")
    private String userName;

    /**
     * Student password
     */
    private String password;

    /**
     * Student courses
     */
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses = new HashSet<>();

    /**
     * check if two Students are equals
     * @param o Student
     * @return boolean two Students are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    /**
     * hash Student
     * @return the hashed id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}





