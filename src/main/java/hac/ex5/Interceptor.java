package hac.ex5;

import hac.ex5.userSession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor as middleware to check authorization
 */
@Component
public class Interceptor implements HandlerInterceptor {

    /**
     * user session to handle login
     */
    @Autowired
    @Qualifier("session")
    private UserSession session;

    /**
     * pre-handler that redirect request to error page if it is not authorized
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        String path = request.getContextPath();

        if (request.getMethod().equals("POST") || uri.contains("password"))
            return true;

        if (session != null) {
            if ((uri.startsWith(path + "/admin") && !session.isAdmin()) ||
                    (uri.startsWith(path + "/student") && !session.isStudent()) ||
                    (uri.startsWith(path + "/lecturer") && !session.isLecturer())) {

                response.sendRedirect("/403");
                return false;
            }
        }
        return true;
    }
}



