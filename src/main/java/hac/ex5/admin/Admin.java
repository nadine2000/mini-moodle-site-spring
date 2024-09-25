package hac.ex5.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * admin class represent th admin that can add students and courses
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id of the admin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * username to login
     */
    @Column(unique = true, nullable = false)
    private String userName;

    /**
     * password to log in
     */
    @NotEmpty
    @NotNull
    private String password;

}

