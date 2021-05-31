package com.example.rps.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {
    private static final String SESSION = "session";
    private final UserRepository underTest = new UserRepository();

    int shouldCreateNewUser() {
        underTest.removeAll();
        int previous_id = underTest.generateId();

        var newUser = underTest.findBySessionOrCreate(SESSION);

        assertEquals(previous_id + 1, newUser.getId());
        assertEquals("User "+newUser.getId(), newUser.getName());
        assertEquals(1, underTest.getAll().size());

        return newUser.getId();
    }

    @Test
    void shouldFindExistingUser() {
        int existingUserId = shouldCreateNewUser();

        var existingUser = underTest.findBySessionOrCreate(SESSION);

        assertEquals(1, underTest.getAll().size());
        assertEquals(existingUserId, existingUser.getId());
        assertEquals(SESSION, existingUser.getSession());
    }

}
