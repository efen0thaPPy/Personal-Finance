package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Model.MyUserDetails;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService {



    @Autowired
    UserRepo repo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repo.findByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("404");
        }
        return new MyUserDetails(user);
    }

    public User saveUser(User user) {
       return repo.save(user);
    }


    public boolean doesUsernameExist(String username) {
       User user=repo.findByUsername(username);
       return user!=null;
    }

    public User findUserByUsername(String username)
    {
       return repo.findByUsername(username);
    }
}
