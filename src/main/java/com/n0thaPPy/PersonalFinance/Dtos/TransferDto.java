package com.n0thaPPy.PersonalFinance.Dtos;


import com.n0thaPPy.PersonalFinance.Model.User;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    @JoinColumn(name = "user_name",referencedColumnName = "username")
    private User receiver;

    private BigDecimal amount;


}
