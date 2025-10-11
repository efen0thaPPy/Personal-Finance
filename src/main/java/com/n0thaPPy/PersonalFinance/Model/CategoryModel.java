package com.n0thaPPy.PersonalFinance.Model;

import com.n0thaPPy.PersonalFinance.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String name;

    @Enumerated(EnumType.STRING)
   private TransactionType type;


}

