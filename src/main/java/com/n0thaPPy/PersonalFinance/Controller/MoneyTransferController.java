package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Dtos.TransferDto;
import com.n0thaPPy.PersonalFinance.Exception.UserNotFound;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MoneyTransferController {

    private final AccountService accountService;

    public MoneyTransferController(AccountService service) {
        this.accountService = service;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?>createTransfer(@RequestBody TransferDto transferDto)
    {
      accountService.processTransfer(transferDto);

       return ResponseEntity.accepted().body(transferDto);
    }

}
