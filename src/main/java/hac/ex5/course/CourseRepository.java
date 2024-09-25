package hac.ex5.course;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CourseRepository for Course table to be able to interact with it.
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
