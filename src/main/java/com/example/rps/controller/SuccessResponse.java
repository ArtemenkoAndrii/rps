package com.example.rps.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class SuccessResponse implements Serializable {
    private List<Round> data;
}
