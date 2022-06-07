package com.example.demo.module.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JwtForm {

    @NotBlank
    private String jwt;

    @NotBlank
    private String username;
}
