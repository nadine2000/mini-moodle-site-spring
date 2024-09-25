package hac.ex5.beanCong;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import hac.ex5.userSession.UserSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Bean Configuration that I used in my program
 */
@Configuration
public class BeanConfiguration {

    /**
     * return the session bean
     * @return the session bean
     */
    @Bean
    @SessionScope
    public UserSession session() {
        return new UserSession();
    }

    /**
     * allow access to all links so the spring security login will not appear.
     * @param http
     * @return Security Filter Chain
     * @throws Exception error occurred
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
        return http.build();
    }

    /**
     * Password Encoder bean to encrypt the passwords
     * @return Password Encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
