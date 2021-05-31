package com.example.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {
    public static final String ROCK = "Rock";
    public static final String PAPER = "Paper";
    public static final String SCISSORS = "Scissors";
    public static final String FIRST_WIN = "First win";
    public static final String SECOND_WIN = "Second win";
    public static final String DRAW = "Draw";

    private static final List<String> VALUES = List.of(ROCK, PAPER, SCISSORS);
    private static final Map<String, String> WINNERS = Map.of(ROCK, SCISSORS, PAPER, ROCK, SCISSORS, PAPER);

    @Autowired
    private Random randomizer;

    public GameResult playRound() {
        String firstPlayer = moveFirstPlayer();
        String secondPlayer = moveSecondPlayer();

        String result;
        if (firstPlayer.equals(secondPlayer)) {
            result = DRAW;
        } else if (secondPlayer.equals(WINNERS.get(firstPlayer))) {
            result = FIRST_WIN;
        } else {
            result = SECOND_WIN;
        }

        return new GameResult(firstPlayer, secondPlayer, result);
    }

    private String moveFirstPlayer() {
        return randomPosition();
    }

    private String moveSecondPlayer() {
        return ROCK;
    }

    private String randomPosition() {
        return VALUES.get(randomizer.nextInt(VALUES.size()));
    }

}
