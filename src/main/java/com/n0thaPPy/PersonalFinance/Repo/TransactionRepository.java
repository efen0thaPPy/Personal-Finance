package com.n0thaPPy.PersonalFinance.Repo;

import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionModel,Integer> {
    @Query("select t from TransactionModel t where t.user.username=:username")
    List<TransactionModel>getTransactionsByUsername(@Param("username")String username);



}
