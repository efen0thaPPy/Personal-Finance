package com.n0thaPPy.PersonalFinance.Exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String username) {
        super(username+" is not found");
    }
}
