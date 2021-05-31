package com.example.rps.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static com.example.rps.service.GameService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GameServiceTest {
    @InjectMocks
    private final GameService underTest = new GameService();

    @Mock
    private Random mockRandomizer;

    @Test
    void shouldPlayWithRockRock() {
        when(mockRandomizer.nextInt(anyInt())).thenReturn(0);

        var game = underTest.playRound();

        assertEquals(ROCK, game.getFirstPlayer());
        assertEquals(ROCK, game.getSecondPlayer());
        assertEquals(DRAW, game.getWinner());
    }

    @Test
    void shouldPlayWithPaperRock() {
        when(mockRandomizer.nextInt(anyInt())).thenReturn(1);

        var game = underTest.playRound();

        assertEquals(PAPER, game.getFirstPlayer());
        assertEquals(ROCK, game.getSecondPlayer());
        assertEquals(FIRST_WIN, game.getWinner());
    }

    @Test
    void shouldPlayWithScissorsRock() {
        when(mockRandomizer.nextInt(anyInt())).thenReturn(2);

        var game = underTest.playRound();

        assertEquals(SCISSORS, game.getFirstPlayer());
        assertEquals(ROCK, game.getSecondPlayer());
        assertEquals(SECOND_WIN, game.getWinner());
    }
}
