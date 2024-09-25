package hac.ex5.controllers;

import hac.ex5.course.Course;
import hac.ex5.services.CourseService;
import hac.ex5.services.LecturerService;
import hac.ex5.services.StudentService;
import hac.ex5.student.Student;
import hac.ex5.userSession.UserSession;
import hac.ex5.admin.Admin;
import hac.ex5.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/**
 * admin controller will handle all admins logic
 */
@Controller
public class AdminController {

    /**
     * student Service to handle student data
     */
    @Autowired
    private StudentService studentService;
    /**
     * user session to handle login
     */
    @Autowired
    @Qualifier("session")
    private UserSession session;
    /**
     * admin Service to handle admin data
     */
    @Autowired
    private AdminService adminService;
    /**
     * course Service to handle course data
     */
    @Autowired
    private CourseService courseService;
    /**
     * lecturer Service to handle lecturer data
     */
    @Autowired
    private LecturerService lecturerService;

    /**
     * return admin home page
     * @return admin home page
     */
    @GetMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin");
    }

    /**
     * return add courses page for the admin, with course and courses list
     * @param model with course and courses list
     * @return add courses page for the admin
     */
    @GetMapping("/admin/add-course")
    public ModelAndView addCourse(ModelMap model) {
        model.addAttribute("course", new Course());
        model.addAttribute("courses", courseService.getCourses());
        return new ModelAndView("add-course", model);
    }

    /**
     * return add courses page for the admin, with course and courses list
     * @param model with course and courses list
     * @return add courses page for the admin
     */
    @GetMapping("/admin/add-student")
    public ModelAndView addStudent(ModelMap model) {
        model.addAttribute("student", new Student());
        model.addAttribute("students", studentService.getStudents());
        return new ModelAndView("add-student", model);
    }

    /**
     * authenticate admin.
     * @param userName admin username
     * @param password admin password
     * @param model to add attribute error
     * @return if authentication was valid return admin page else stay at the login page and show error
     */
    @PostMapping("/admin")
    @ResponseBody
    public ModelAndView authenticate(@RequestParam("userName") String userName,
                                     @RequestParam("password") String password, ModelMap model) {
        Admin admin = adminService.authenticate(userName, password);
        if (admin != null) {
            session.setRole("admin");
            return new ModelAndView("admin", model);
        }
        model.addAttribute("error", true);
        return new ModelAndView("redirect:/login/admin", model);
    }

    /**
     * authenticate admin then change his password, then logout.
     * @param userName admin username
     * @param password admin password
     * @param newPassword admin new password
     * @param model to add attribute error
     * @return if the authentication and new password was valid return to home page else stay at the login page and show error
     */
    @PostMapping("/admin/password")
    @ResponseBody
    public ModelAndView changePassword(@RequestParam("userName") String userName,
                                       @RequestParam("password") String password,
                                       @RequestParam("newPassword") String newPassword, ModelMap model) {

        Admin admin = adminService.authenticate(userName, password);
        if (admin != null) {
            session.setRole("");
            adminService.updatePassword(admin, newPassword);
            return new ModelAndView("redirect:/");
        }
        model.addAttribute("error", true);
        return new ModelAndView("redirect:/admin/password", model);
    }

    /**
     * add Course to courses list if  the course is valid.
     * @param course the new course
     * @param result if there is an error in the course data
     * @param model to add course attributes or error
     * @return the add course page with error or new data
     */
    @PostMapping("/admin/add-course")
    @ResponseBody
    public ModelAndView addCourse(@Valid Course course, BindingResult result, ModelMap model) {

        model.addAttribute("course", course);
        model.addAttribute("courses", courseService.getCourses());

        if (result.hasErrors()) {
            return new ModelAndView("add-course");
        }

        try {
            courseService.saveCourse(course);
            lecturerService.saveLecturer(course);
        } catch (DataIntegrityViolationException e)
        {
            model.addAttribute("error", "course name must be unique, this course already existed!");
            return new ModelAndView("add-course");
        }
        return new ModelAndView("redirect:/admin/add-course");
    }

    /**
     * add new student to the table
     * @param student the new student
     * @param result if there is an error in the course data
     * @param model to add student attributes
     * @return add student page
     */
    @PostMapping("/admin/add-student")
    @ResponseBody
    public ModelAndView addStudent(@Valid Student student, BindingResult result, ModelMap model) {

        model.addAttribute("student", student);
        model.addAttribute("students", studentService.getStudents());

        if (result.hasErrors()) {
            return new ModelAndView("add-student");
        }

        try {
            studentService.saveStudent(student);
        } catch (DataIntegrityViolationException e)
        {
            model.addAttribute("error", "student name must be unique, this student already existed!");
            return new ModelAndView("add-student");
        }

        return new ModelAndView("redirect:/admin/add-student");
    }

    /**
     * show edite student page if the id of the student is valid
     * else stay at the same page
     * @param id of the wanted student
     * @param model to add student attribute
     * @return update-student page or the same page if the id is not valid
     */
    @PostMapping("/admin/edite-student")
    public ModelAndView editStudent(@RequestParam("id") long id, ModelMap model) {

        Student student = studentService.getStudent(id).orElse(null);
        if (student == null)
            return new ModelAndView("redirect:/admin/add-student");

        model.addAttribute("student", student);
        return new ModelAndView("update-student", model);
    }

    /**
     * edite student with given id in the link to the new student inputted
     * if the id is not valid return to add student page
     * @param id of the wanted student
     * @param student new student data
     * @param result if the student data has some errors
     * @param model to add student attribute
     * @return add student page
     */
    @PostMapping("/admin/edite-student/{id}")
    public ModelAndView updateStudent(@PathVariable("id") long id, @Valid Student student,
                                      BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return new ModelAndView("update-student");
        }

        Student s = studentService.getStudent(id).orElse(null);

        if (s == null)
            return new ModelAndView("redirect:/admin/add-student");

        s.setUserName(student.getUserName());
        studentService.updateStudent(s);

        return new ModelAndView("redirect:/admin/add-student");
    }

    /**
     * delete course with the requested id
     * @param id of the course we wnt to delete
     * @return add course page
     */
    @PostMapping("/admin/delete-course")
    public ModelAndView deleteCourse(@RequestParam("id") long id) {
        lecturerService.deleteCourse(courseService.getCourse(id).orElse(null));
        courseService.deleteCourse(id);
        return new ModelAndView("redirect:/admin/add-course");
    }
}
