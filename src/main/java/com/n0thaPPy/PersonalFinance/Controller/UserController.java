package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Dtos.EntryDto;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Roles;
import com.n0thaPPy.PersonalFinance.Service.JwtService;
import com.n0thaPPy.PersonalFinance.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService detailsService;

    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    @Autowired
   private AuthenticationManager manager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody EntryDto user)
    {

        if(detailsService.doesUsernameExist(user.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of
            ("result","not successful","message","username: "+ user.getUsername()+" is already taken choose another one"));

        }
        else
        {

            String password=passwordEncoder.encode(user.getPassword());

            User newUser=new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(password);
            newUser.getRoles().add(Roles.ROLE_USER);
            detailsService.saveUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("result", "Success",
                            "message", "User is registered successfully",
                            "username", newUser.getUsername())
            );
        }



    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EntryDto user)
    {
       Authentication authentication=manager.authenticate
               (new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

       if(authentication.isAuthenticated())
       {

         User currentUser=detailsService.findUserByUsername(user.getUsername());
          String token=jwtService.generateToken(currentUser.getUsername(),currentUser.getRoles());

          return ResponseEntity.ok(Map.of("result","successful",
                  "message","Login Successful",
                  "token",token));
       }
       else
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body
                   (Map.of("result","Not Successful","message","Wrong Credentials"));


    }

}
