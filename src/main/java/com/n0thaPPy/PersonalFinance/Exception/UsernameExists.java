package com.n0thaPPy.PersonalFinance.Exception;

public class UsernameExists extends RuntimeException {
    public UsernameExists(String message) {
        super(message + "is already exists");
    }
}
