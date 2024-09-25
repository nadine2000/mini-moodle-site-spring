package hac.ex5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * handle general links
 */
@Controller
public class HomeController {
    /**
     * return home page
     * @return home page
     */
    @GetMapping("/")
    public String home() { return "index";}

    /**
     * return error page
     * @return error page
     */
    @GetMapping("/error")
    public String error() { return "error"; }

    /**
     * return authorization error page
     * @return authorization error page
     */
    @GetMapping("/403")
    public String error403() { return "403"; }

}
