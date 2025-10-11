package com.n0thaPPy.PersonalFinance.Model;

import com.n0thaPPy.PersonalFinance.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal money;

    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

}
