package com.n0thaPPy.PersonalFinance.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonBackReference
    private User user;
}
