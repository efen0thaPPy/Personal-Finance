package com.n0thaPPy.PersonalFinance.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "account_table")
public class AccountModel {

    public AccountModel()
    {
        balance=new BigDecimal(0);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User user;
}
