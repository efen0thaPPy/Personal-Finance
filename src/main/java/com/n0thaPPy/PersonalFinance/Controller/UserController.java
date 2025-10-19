package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Dtos.EntryDto;
import com.n0thaPPy.PersonalFinance.Dtos.UserDto;
import com.n0thaPPy.PersonalFinance.Exception.WrongCredentials;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Roles;
import com.n0thaPPy.PersonalFinance.Service.JwtService;
import com.n0thaPPy.PersonalFinance.Service.MyUserDetailsService;
import com.n0thaPPy.PersonalFinance.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody EntryDto user) {

        User newUser = userService.registerUser(user);
        UserDto userDto=new UserDto();
        userDto.setUsername(newUser.getUsername());
        userDto.setRolesSet(newUser.getRoles());

        return ResponseEntity.ok(userDto);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EntryDto user)
    {

            Authentication authentication=manager.authenticate
                    (new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

            if(authentication.isAuthenticated())
            {

                User currentUser=userService.findUserByUsername(user.getUsername());
                String token=jwtService.generateToken(currentUser.getUsername(),currentUser.getRoles());

                return ResponseEntity.ok(Map.of("result","successful",
                        "message","Login Successful",
                        "token",token));
            }
            else
                throw new WrongCredentials("wrong credentials");



    }

    @GetMapping("/api/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>getUsers()
    {
       List<User> users=userService.findAllUsers();

      return ResponseEntity.ok(users);
    }

}
