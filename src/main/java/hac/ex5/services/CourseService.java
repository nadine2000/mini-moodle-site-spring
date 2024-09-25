package hac.ex5.services;

import hac.ex5.course.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

//    @Transactional
//    public void addCourses(ArrayList<Course> users) {
//        for (Course user : users) {
//            courseRepository.save(user);
//        }
//    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }
    public Optional<Course> getCourse(long id) {
        return courseRepository.findById(id);
    }
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}