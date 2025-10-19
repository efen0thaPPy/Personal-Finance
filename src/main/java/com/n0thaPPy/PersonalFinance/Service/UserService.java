package com.n0thaPPy.PersonalFinance.Service;


import com.n0thaPPy.PersonalFinance.Dtos.EntryDto;
import com.n0thaPPy.PersonalFinance.Exception.UsernameExists;
import com.n0thaPPy.PersonalFinance.Model.AccountModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Repo.AccountRepo;
import com.n0thaPPy.PersonalFinance.Repo.UserRepo;
import com.n0thaPPy.PersonalFinance.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerUser(EntryDto user) {

        if(doesUsernameExist(user.getUsername()))
        {
            throw new UsernameExists(user.getUsername());
        }
        else {
            User newUser=new User();
            String password= passwordEncoder.encode(user.getPassword());
            AccountModel accountModel=new AccountModel();

            accountModel.setUser(newUser);

            newUser.setPassword(password);
            newUser.setUsername(user.getUsername());
            newUser.getRoles().add(Roles.ROLE_USER);

            newUser.setAccountModel(accountModel);
            userRepo.save(newUser);


            return newUser;

        }


    }


    public boolean doesUsernameExist(String username) {
        User user=userRepo.findByUsername(username);
        return user!=null;
    }

    public User findUserByUsername(String username)
    {
        return userRepo.findByUsername(username);
    }
    public List<User> findAllUsers()
    {
       return userRepo.findAll();
    }
}
