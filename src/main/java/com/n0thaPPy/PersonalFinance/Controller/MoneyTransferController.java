package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Dtos.AccountDto;
import com.n0thaPPy.PersonalFinance.Dtos.TransferDto;
import com.n0thaPPy.PersonalFinance.Exception.UserNotFound;
import com.n0thaPPy.PersonalFinance.Model.AccountModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MoneyTransferController {

    private final AccountService accountService;

    public MoneyTransferController(AccountService service) {
        this.accountService = service;
    }
    /*
    it might not seem necessary for PersonalFinance app
     however, I wanted to add it in purpose of demonstrating my skills
     */
    @PostMapping("/transfer")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?>createTransfer(@RequestBody TransferDto transferDto)
    {
      accountService.processTransfer(transferDto);

       return ResponseEntity.accepted().body(transferDto);
    }
    @PreAuthorize("hasRole('ADMIN') or #username==authentication.principal")
    @GetMapping("/transfer/{username}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?>getAccount(@PathVariable String username)
    {
      AccountDto accountDto=accountService.getAccount(username);

      return ResponseEntity.ok(accountDto);


    }



}
