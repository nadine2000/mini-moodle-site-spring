package hac.ex5.services;


import hac.ex5.lecturer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import hac.ex5.course.Course;
import hac.ex5.lecturer.Lecturer;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Lecturer authenticate(String name, String password) {
        Lecturer user = lecturerRepository.findByUserName(name);
        return (user != null && passwordEncoder.matches(password, user.getPassword())) ? user : null ;
    }

    public void updatePassword(Lecturer user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        lecturerRepository.save(user);
    }

    public void saveLecturer(Course course)
    {
        if (course != null && course.getLecturerUserName() != null) {
            Lecturer l = lecturerRepository.findByUserName(course.getLecturerUserName());
            if (l == null) {
                Lecturer  lecturer = new Lecturer(course.getLecturerUserName(), passwordEncoder.encode(course.getLecturerUserName()));
                lecturer.getCourses().add(course);
                lecturerRepository.save(lecturer);
            } else {
                if (l.getCourses() != null) {
                    l.getCourses().add(course);
                    lecturerRepository.save(l);
                }
            }
        }
    }

    public void deleteCourse(Course course) {
        if (course != null) {
            String lecturerUserName = course.getLecturerUserName();
            Lecturer lecturer = lecturerRepository.findByUserName(lecturerUserName);
            if (lecturer != null && lecturer.getCourses() != null) {
                if (lecturer.getCourses().contains(course)) {
                    lecturer.getCourses().remove(course);
                    lecturerRepository.save(lecturer);
                }
            }
        }
    }

}
