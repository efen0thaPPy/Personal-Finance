package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.Repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repo;

    public List<TransactionModel> getTransactions() {
        return repo.findAll();
    }

    public TransactionModel createTransaction(TransactionModel transactionModel)
    {
        return repo.save(transactionModel);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public List<TransactionModel> getTransaction(String username)
    {
    return repo.getTransactionsByUsername(username);
    }

    public Optional<TransactionModel> getTransactionById(int id) {

        return repo.findById(id);
    }
}
