

package hac.ex5.controllers;

import hac.ex5.course.Course;
import hac.ex5.services.CourseService;
import hac.ex5.services.LecturerService;
import hac.ex5.lecturer.Lecturer;
import hac.ex5.userSession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * lecturer controller will handle all lecturers logic
 */
@Controller
public class LecturerController {

    /**
     * user session to handle login
     */
    @Autowired
    @Qualifier("session")
    private UserSession session;

    /**
     * lecturer Service to handle lecturer data
     */
    @Autowired
    private LecturerService lecturerService;

    /**
     * course Service to handle course data
     */
    @Autowired
    private CourseService courseService;

    /**
     * the current logged in lecturer
     */
    private Lecturer currentLecturer;

    /**
     * return lecturer home page
     * @param model add username and courses attributes
     * @return lecturer home page
     */
    @GetMapping("/lecturer")
    public ModelAndView lecturer(ModelMap model) {
        model.addAttribute("userName", currentLecturer.getUserName());
        model.addAttribute("courses", currentLecturer.getCourses());
        return new ModelAndView("lecturer", model);
    }

    /**
     * authenticate lecturer then change his password, then logout.
     * @param userName lecturer username
     * @param password lecturer password
     * @param newPassword lecturer new password
     * @param model to add attribute error
     * @return if the authentication and new password was valid return to home page else stay at the login page and show error
     */
    @PostMapping("/lecturer/password")
    @ResponseBody
    public ModelAndView changePassword(@RequestParam("userName") String userName,
                                       @RequestParam("password") String password,
                                      @RequestParam("newPassword") String newPassword, ModelMap model) {

        Lecturer lecturer = lecturerService.authenticate(userName, password);
        if (lecturer != null) {
            session.setRole("");
            lecturerService.updatePassword(lecturer, newPassword);
            return new ModelAndView("redirect:/");
        }
        model.addAttribute("error", true);
        return new ModelAndView("redirect:/lecturer/password", model);
    }

    /**
     * authenticate lecturer.
     * @param userName lecturer username
     * @param password lecturer password
     * @param model to add attribute error
     * @return if authentication was valid return lecturer page else stay at the login page and show error
     */
    @PostMapping("/lecturer")
    @ResponseBody
    public ModelAndView authenticate(@RequestParam("userName") String userName,
                                     @RequestParam("password") String password, ModelMap model) {
        currentLecturer = lecturerService.authenticate(userName, password);
        if (currentLecturer != null) {
            session.setRole("lecturer");
            model.addAttribute("userName", currentLecturer.getUserName());
            model.addAttribute("courses", currentLecturer.getCourses());
            return new ModelAndView("lecturer", model);
        }
        model.addAttribute("error", true);
        return new ModelAndView("redirect:/login/lecturer", model);
    }

    /**
     * show course page of the given id.
     * @param id course id
     * @param model add attributes role course and students
     * @return course page or error page if the id is invalid
     */
    @GetMapping("/lecturer/{id}")
    public ModelAndView course(@PathVariable long id, ModelMap model) {
        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            model.addAttribute("role", "teacher");
            model.addAttribute("course", course);
            model.addAttribute("students", course.getStudents());
            return new ModelAndView("course-teacher", model);
        }
        return new ModelAndView("error", model);
    }

    /**
     * add descriptions to the course.
     * @param id course id
     * @param descriptions new description
     * @return course page
     */
    @PostMapping("/lecturer/add-des")
    public ModelAndView addDes(@RequestParam("id") long id, @RequestParam("descriptions") String descriptions) {
        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            course.setDescriptions(descriptions);
            courseService.saveCourse(course);
            return new ModelAndView("redirect:/lecturer/" + id);
        }
        return new ModelAndView("error");
    }

    /**
     * add message to course forum
     * @param id course id
     * @param mes new message
     * @return course page
     */
    @PostMapping("/lecturer/add-message")
    public ModelAndView addForum(@RequestParam("id") long id, @RequestParam("message") String mes) {
        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            course.getForum().add(mes);
            courseService.saveCourse(course);
            return new ModelAndView("redirect:/lecturer/" + id);
        }
        return new ModelAndView("error");
    }
}