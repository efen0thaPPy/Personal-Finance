package com.n0thaPPy.PersonalFinance.Repo;

import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel,Integer> {

}
