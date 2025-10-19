package com.n0thaPPy.PersonalFinance.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.n0thaPPy.PersonalFinance.Model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private BigDecimal balance;

    private String username;
}
