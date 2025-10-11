package com.n0thaPPy.PersonalFinance.Repo;

import com.n0thaPPy.PersonalFinance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
}
