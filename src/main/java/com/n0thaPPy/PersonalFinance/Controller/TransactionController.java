package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.Service.TransactionService;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping("/transactions")
    public List<TransactionModel>getTransactions()
    {
       return service.getTransactions();
    }
    @PostMapping("/transaction")
    public ResponseEntity<TransactionModel> saveTransaction(@RequestBody TransactionModel transactionModel)
    {
        TransactionModel savedTransaction=service.createTransaction(transactionModel);

        return ResponseEntity.ok(savedTransaction);
    }
    @PutMapping("/transaction")
    public ResponseEntity<TransactionModel> updateTransaction(@RequestBody TransactionModel transactionModel)
    {
        TransactionModel savedTransaction=service.createTransaction(transactionModel);

        return ResponseEntity.ok(savedTransaction);
    }
    @DeleteMapping("/transaction/{id}")
    public void deleteTransaction(@PathVariable int id)
    {
       service.deleteById(id);


    }


}
