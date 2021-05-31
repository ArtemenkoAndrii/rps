package com.example.rps.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoundRepositoryTest {
    private static final String NAME = "name";

    private final RoundRepository underTest = new RoundRepository();

    @Test
    void shouldCreateRound() {
        int previous_id = underTest.generateId();
        var round = underTest.createRound();
        assertEquals(previous_id + 1, round.getId());
        assertNull(round.getFirstPlayer());
        assertNull(round.getSecondPlayer());
        assertNull(round.getWinner());
    }

    @Test
    void shouldFindByUser() {
        var round1 = underTest.createRound();
        int originalId = round1.getId();
        round1.setUser(NAME);
        underTest.add(round1);
        var round2 = underTest.createRound();
        round2.setUser(NAME+NAME);
        underTest.add(round2);

        var foundUser = underTest.findByUser(NAME);
        assertEquals(1, foundUser.size());
        assertEquals(originalId, foundUser.stream().findFirst().get().getId());
        assertEquals(NAME, foundUser.stream().findFirst().get().getUser());
    }

}
