package hac.ex5.admin;

import hac.ex5.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Admin Initializer class will create initial username and password for admin to be able
 * to add students and courses
 */
@Component
public class AdminInitializer implements CommandLineRunner {
    /**
     * admin Service to save admin data
     */
    private final AdminService adminService;

    /**
     * constructor that takes adminService
     * @param adminService
     */
    @Autowired
    public AdminInitializer(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * before even the program run create admin with username and password "admin"
     * if the admin table is empty
     * @param args
     */
    @Override
    public void run(String... args) {
        if (adminService.count() == 0) {
            adminService.addAdmin("admin", "admin");
        }
    }
}