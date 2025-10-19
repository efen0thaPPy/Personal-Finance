package com.n0thaPPy.PersonalFinance.Dtos;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class TransactionReceiveDto {
    private BigDecimal money;

    private String description;
    private LocalDate date;

    private CategoryModel category;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
