package com.n0thaPPy.PersonalFinance.Dtos;

import com.n0thaPPy.PersonalFinance.Roles;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    String username;
    Set<Roles> rolesSet=new HashSet<>();
}
