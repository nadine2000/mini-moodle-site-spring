package hac.ex5.services;

import hac.ex5.admin.Admin;
import hac.ex5.admin.AdminRepository;
import hac.ex5.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public void updatePassword(Admin user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        adminRepository.save(user);
    }

    public void addAdmin(String username, String password)
    {
        Admin admin = new Admin();
        admin.setUserName(username);
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
    }
    public Admin authenticate(String username, String password) {
        Admin user = adminRepository.findByUserName(username);
        return (user != null && passwordEncoder.matches(password, user.getPassword())) ? user : null ;
    }
    public long count()
    {
        return adminRepository.count();
    }
}
