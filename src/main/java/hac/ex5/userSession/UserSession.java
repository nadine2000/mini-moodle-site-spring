package hac.ex5.userSession;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * UserSession class to save login data roles
 */
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserSession implements Serializable {

    private static final long serialVersionUID = 288L;

    /**
     * logged in role
     */
    private String role;

    /**
     * constructor that make role empty
     */
    public UserSession() { this.role = ""; }

    /**
     * check if the role is valid (admin or student or lecturer)
     * @param r role to check
     * @return boolean valid role or not
     */
    public boolean validRole(String r) { return r != null && (r.equals("admin") || r.equals("student") || r.equals("lecturer")); }

    /**
     * the current logged-in user is admin
     * @return the current logged-in user is admin
     */
    public boolean isAdmin() { return role != null && role.equals("admin"); }
    /**
     * the current logged-in user is student
     * @return the current logged-in user is student
     */
    public boolean isStudent()
    {
        return role != null  && role.equals("student");
    }
    /**
     * the current logged-in user is lecturer
     * @return the current logged-in user is lecturer
     */
    public boolean isLecturer()
    {
        return role != null && role.equals("lecturer");
    }

}
