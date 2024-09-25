package hac.ex5.admin;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AdminRepository for admin table to be able to interact with it.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
    /**
     * find admin by its username and return it. if it does not exist, return null
     * @param userName admin username
     * @return null or the admin with the requested username
     */
    Admin findByUserName(String userName);

}
