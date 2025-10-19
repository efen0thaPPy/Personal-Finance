package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Dtos.TransactionDto;
import com.n0thaPPy.PersonalFinance.Dtos.TransactionReceiveDto;
import com.n0thaPPy.PersonalFinance.Dtos.TransactionUpdateReceiverDto;
import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.Service.TransactionService;
import com.n0thaPPy.PersonalFinance.TransactionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/transactions")
    public List<TransactionDto>getTransactions()
    {
       return service.getTransactions().stream().map(TransactionDto::new).toList();
    }
    @PreAuthorize("#username==authentication.principal or hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/transaction/{username}")
    public ResponseEntity<List<TransactionDto>>getTransactionByUsername(@PathVariable String username)
    {
       List<TransactionModel> transactionModel=service.getTransaction(username);

       List<TransactionDto>transactionDto=transactionModel.stream().
               map(TransactionDto::new).toList();



             return  ResponseEntity.ok(transactionDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDto>getTransactionById(@PathVariable int id)
    {
        Optional<TransactionModel> transactionModel=service.getTransactionById(id);

        if(transactionModel.isPresent())
        {
            TransactionDto dto=new TransactionDto(transactionModel.get());
            return  ResponseEntity.ok(dto);
        }
        else
        {
            return ResponseEntity.notFound().build();

        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDto> saveTransaction(@RequestBody TransactionReceiveDto transactionReceiveDto)
    {
        TransactionModel savedTransaction=service.createTransaction(transactionReceiveDto);

        TransactionDto transactionDto=new TransactionDto(savedTransaction);

        return ResponseEntity.ok(transactionDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/transaction")
    public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionUpdateReceiverDto transactionReceiveDto)
    {
        TransactionModel savedTransaction=service.updateTransaction(transactionReceiveDto);

        TransactionDto transactionDto=new TransactionDto(savedTransaction);

        return ResponseEntity.ok(transactionDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id)
    {
        service.deleteById(id);
      return ResponseEntity.noContent().build();


    }


}
