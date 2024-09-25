package hac.ex5.lecturer;

import hac.ex5.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Lecturer class represent the Lecturer that can change in courses data
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lecturer implements Serializable {
    private static final long serialVersionUID = 9L;

    /**
     * Lecturer id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Lecturer username
     */
    @Column(unique = true, nullable = false)
    private String userName;

    /**
     * Lecturer password
     */
    private String password;

    /**
     * Lecturer courses
     */
    @OneToMany
    private Set<Course> courses = new HashSet<>();

    /**
     * Lecturer constructor to add username and password
     * @param userName
     * @param password
     */
    public Lecturer(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }
}