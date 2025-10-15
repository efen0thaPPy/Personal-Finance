package com.n0thaPPy.PersonalFinance.Model;

import com.n0thaPPy.PersonalFinance.Dtos.EntryDto;
import com.n0thaPPy.PersonalFinance.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user_table")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String username;
    private String password;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")

    )
    @Column(name="role")
    private Set<Roles> roles=new HashSet<>();

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountModel accountModel;
}
