package com.n0thaPPy.PersonalFinance.Dtos;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {

    public TransactionDto(TransactionModel model) {
        this.id = model.getId();
        this.money = model.getMoney();
        this.description = model.getDescription();
        this.date = model.getDate();
        this.type = model.getType();
        this.categoryName=model.getCategory().getName();
        this.username=model.getUser().getUsername();
    }

    private int id;
    private BigDecimal money;
    private String description;
    private LocalDate date;
    private String categoryName;
    private String username;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

}
