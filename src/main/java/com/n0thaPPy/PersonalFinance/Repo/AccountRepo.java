package com.n0thaPPy.PersonalFinance.Repo;

import com.n0thaPPy.PersonalFinance.Model.AccountModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepo extends JpaRepository<AccountModel,Integer> {

    AccountModel findAccountModelByUser(User user);
    @Query("select t from AccountModel t where t.user.username=:username")
    AccountModel findByUserUsername(@Param("username") String username);
}
