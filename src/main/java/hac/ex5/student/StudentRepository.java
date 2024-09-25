package hac.ex5.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * StudentRepository for Student table to be able to interact with it.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * find Lecturer by its username and return it. if it does not exist, return null
     * @param id Student id
     * @return nothing or the Student with the requested id
     */
    Optional<Student> findById(long id);
}
