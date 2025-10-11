package com.n0thaPPy.PersonalFinance.Dtos;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDto {
    private BigDecimal money;

    private String description;
    private LocalDate date;

    private CategoryModel category;


    private TransactionType type;
}
