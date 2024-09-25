package hac.ex5.services;

import hac.ex5.admin.Admin;
import hac.ex5.lecturer.Lecturer;
import hac.ex5.student.Student;
import hac.ex5.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void updatePassword(Student user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        studentRepository.save(user);
    }

    public Student authenticate(long id, String password) {
        Student user = studentRepository.findById(id).orElse(null);
        return (user != null && passwordEncoder.matches(password, user.getPassword())) ? user : null ;
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public void saveStudent(Student student) {
        if (studentRepository.findById(student.getId()).orElse(null) != null)
            throw new DataIntegrityViolationException("");
        student.setPassword(passwordEncoder.encode(String.valueOf(student.getId())));
        studentRepository.save(student);
    }
    public void updateStudent(Student s ){
        studentRepository.save(s);
    }
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    public Optional<Student> getStudent(long id) {
        return studentRepository.findById(id);
    }
}
