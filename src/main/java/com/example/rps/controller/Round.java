package com.example.rps.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Round {
    private String type;
    private Integer id;
    private Attributes attributes;
}
