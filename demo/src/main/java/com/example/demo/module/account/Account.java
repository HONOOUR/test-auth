package com.example.demo.module.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    int phoneNumber;

}