package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Service.JwtService;
import com.n0thaPPy.PersonalFinance.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private JwtService service;

    @Autowired
    private MyUserDetailsService detailsService;

    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    @Autowired
   private AuthenticationManager manager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user)
    {

        if(detailsService.findByUsername(user.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of
            ("result","not successful","message","username"+ user.getUsername()+" is already taken choose another one"));

        }
        else
        {
            String password=passwordEncoder.encode(user.getPassword());
            User encryptedUser=new User(user.getId(),user.getUsername(),password);
            User savedUser= detailsService.saveUser(encryptedUser);
            return ResponseEntity.ok(savedUser);
        }



    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user)
    {
       Authentication authentication=manager.authenticate
               (new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

       if(authentication.isAuthenticated())
       {
           return service.generateToken(user.getUsername());
       }
       else
           return "Failed";


    }

}
