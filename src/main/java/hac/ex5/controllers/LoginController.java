package hac.ex5.controllers;

import hac.ex5.userSession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/**
 * handle login related links
 */
@Controller
public class LoginController {

    /**
     * user session to handle login
     */
    @Autowired
    @Qualifier("session")
    private UserSession session;

    /**
     * show login to the role. if already logged in, redirect to role home page.
     * if there is an error show it.
     * @param role the requested role
     * @param error error in logged in data
     * @param model to add error attribute
     * @return role home page
     */
    @GetMapping("/login/{role}")
    public ModelAndView login(@PathVariable String role, @RequestParam(value = "error", required = false) String error,
                              ModelMap model) {

        if (!session.validRole(session.getRole()))
        {
            if (session.validRole(role))
            {
                model.addAttribute("error", error);
                model.addAttribute("role", role);
                return new ModelAndView("login", model);
            }
        }
        else
        {
            model.addAttribute("role", session.getRole());
            return new ModelAndView("redirect:/" + session.getRole());
        }

        return new ModelAndView("error");
    }

    /**
     * change "role" password. if already logged in redirect to "role" change password page.
     * @param role the requested role
     * @param error error in logged in data
     * @param model to add error attribute
     * @return password change page
     */
    @GetMapping("/login/{role}/password")
    public ModelAndView password(@PathVariable String role,@RequestParam(value = "error", required = false) String error,
                             ModelMap model) {

        if (session.validRole(role))
        {
            model.addAttribute("error", error);
            model.addAttribute("role", role);

            if (session.validRole(session.getRole()))
            {
                if  (session.getRole().equals(role))
                    return new ModelAndView("password", model);
                else
                    return new ModelAndView("redirect:/" + session.getRole());

            }
            return new ModelAndView("password", model);

        }
        return new ModelAndView("error");
    }

    /**
     * destroy session (logout)
     * @param request servlet request
     * @return home page
     */
    @PostMapping("/destroy")
    public ModelAndView destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }
}
