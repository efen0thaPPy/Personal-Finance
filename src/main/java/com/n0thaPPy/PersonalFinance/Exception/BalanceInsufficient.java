package com.n0thaPPy.PersonalFinance.Exception;

import java.math.BigDecimal;

public class BalanceInsufficient extends RuntimeException {

    public BalanceInsufficient(BigDecimal currentBalance,BigDecimal moneyToBeSent) {
        super("the money you want send can't exceed the balance you have:\n" +
                "Balance: "+currentBalance+"\n"+
                "MoneyToBeSent: "+moneyToBeSent);
    }
}
