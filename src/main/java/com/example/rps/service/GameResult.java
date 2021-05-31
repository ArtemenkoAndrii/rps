package com.example.rps.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameResult {
    private final String firstPlayer;
    private final String secondPlayer;
    private final String winner;
}
