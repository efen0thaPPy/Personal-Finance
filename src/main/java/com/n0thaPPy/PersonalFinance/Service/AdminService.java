package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Repo.UserRepo;
import com.n0thaPPy.PersonalFinance.Roles;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @EventListener(ApplicationReadyEvent.class)
    public void addAdminUser()
    {
        Dotenv dotenv=Dotenv.load();
        String username=dotenv.get("ADMIN_USERNAME");
        String password=dotenv.get("ADMIN_PASSWORD");

        if(username ==null || password==null) {
            System.out.println("username or password is null");
            return;
        }

        User existingUser= repo.findByUsername(username);

            if (existingUser == null) {

                User adminUser = new User();
                adminUser.setUsername(username);

                String encryptedPassword = encoder.encode(password);

                adminUser.setPassword(encryptedPassword);
                adminUser.getRoles().add(Roles.ROLE_USER);
                adminUser.getRoles().add(Roles.ROLE_ADMIN);

                repo.save(adminUser);
            }



    }

}
