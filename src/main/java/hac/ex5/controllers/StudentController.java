package hac.ex5.controllers;


import hac.ex5.course.Course;
import hac.ex5.services.CourseService;
import hac.ex5.student.Student;
import hac.ex5.services.StudentService;
import hac.ex5.userSession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StudentController {

    /**
     * user session to handle login
     */
    @Autowired
    @Qualifier("session")
    private UserSession session;

    /**
     * student Service to handle student data
     */
    @Autowired
    private StudentService studentService;

    /**
     * course Service to handle course data
     */
    @Autowired
    private CourseService courseService;

    /**
     * the current logged in student
     */
    private Student currentStudent;

    /**
     * return student home page
     * @param model add username and courses attributes
     * @return student home page
     */
    @GetMapping("/student")
    public ModelAndView student(ModelMap model) {
        model.addAttribute("userName", currentStudent.getUserName());
        model.addAttribute("courses", currentStudent.getCourses());
        return new ModelAndView("student", model);
    }

    /**
     * authenticate student then change his password, then logout.
     * @param userName student username
     * @param password student password
     * @param newPassword student new password
     * @param model to add attribute error
     * @return if the authentication and new password was valid return to home page else stay at the login page and show error
     */
    @PostMapping("/student/password")
    @ResponseBody
    public ModelAndView changePassword(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                      @RequestParam("newPassword") String newPassword, ModelMap model) {

        try {
            Student student = studentService.authenticate(Long.parseLong(userName), password);

            if (student != null) {
                session.setRole("");
                studentService.updatePassword(student, newPassword);
                return new ModelAndView("redirect:/");
            }

        } catch (NumberFormatException ignored){
        } finally {
            model.addAttribute("error", true);
        }
        return new ModelAndView("redirect:/student/password", model);
    }

    /**
     * show course page of the given id.
     * @param id course id
     * @param model add attributes role course and students
     * @return course page or error page if the id is invalid
     */
    @GetMapping("/student/{id}")
    public ModelAndView course(@PathVariable long id, ModelMap model) {
        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            model.addAttribute("role", "student");
            model.addAttribute("course", course);
            return new ModelAndView("course-teacher", model);
        }
        return new ModelAndView("redirect:/error");
    }

    /**
     * authenticate student.
     * @param userName student username
     * @param password student password
     * @param model to add attribute error
     * @return if authentication was valid return student page else stay at the login page and show error
     */
    @PostMapping("/student")
    @ResponseBody
    public ModelAndView authenticate(@RequestParam("userName") String userName, @RequestParam("password") String password, ModelMap model) {

        try {
            currentStudent = studentService.authenticate(Long.parseLong(userName), password);

            if (currentStudent != null) {
                session.setRole("student");
                model.addAttribute("userName", currentStudent.getUserName());
                model.addAttribute("courses", currentStudent.getCourses());
                return new ModelAndView("student");
            }

        } catch (NumberFormatException ignored){
        } finally {
            model.addAttribute("error", true);
        }
        return new ModelAndView("redirect:/login/student", model);
    }

    /**
     * show courses to student
     * @param model add courses list to model
     * @return student-add-course page
     */
    @GetMapping("/student/add-courses")
    public ModelAndView addCourse(ModelMap model) {
        List<Course> courses = courseService.getCourses();
        courses.removeAll(currentStudent.getCourses());
        model.addAttribute("courses", courses);
        return new ModelAndView("student-add-course", model);
    }

    /**
     * remove course from student courses
     * @param id course id
     * @return student page
     */
    @GetMapping("student/delete/{id}")
    public ModelAndView deleteCourse(@PathVariable("id") long id) {
        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            currentStudent.getCourses().remove(course);
            studentService.updateStudent(currentStudent);
            course.getStudents().remove(currentStudent);
            courseService.saveCourse(course);
            return new ModelAndView("redirect:/student");
        }
        return new ModelAndView("redirect:/error");
    }

    /**
     * add course to student courses
     * @param id course id
     * @return
     */
    @PostMapping("student/add")
    public ModelAndView addCourse(@RequestParam("id") long id) {

        Course course = courseService.getCourse(id).orElse(null);
        if (course != null) {
            try {
                currentStudent.getCourses().add(course);
                studentService.updateStudent(currentStudent);
                course.getStudents().add(currentStudent);
                courseService.saveCourse(course);
            } catch (Exception ignored) {
            } finally {
                return new ModelAndView("redirect:/student/add-courses");
            }
        }
        return new ModelAndView("redirect:/error");
    }

}


