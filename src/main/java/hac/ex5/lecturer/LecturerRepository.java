package hac.ex5.lecturer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LecturerRepository for Lecturer table to be able to interact with it.
 */
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    /**
     * find Lecturer by its username and return it. if it does not exist, return null
     * @param userName Lecturer username
     * @return null or the Lecturer with the requested username
     */
    Lecturer findByUserName(String userName);

}
