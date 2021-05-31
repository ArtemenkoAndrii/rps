package com.example.rps.repository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryRepositoryTest {
    private static final int ID1 = 1;
    private static final String NAME1 = "Name1";
    private static final int ID2 = 2;
    private static final String NAME2 = "Name2";

    private final InMemoryRepository<TestEntity> underTest = new InMemoryRepository<>();

    @BeforeEach
    void setUp() {
        underTest.add(new TestEntity(ID1, NAME1));
        underTest.add(new TestEntity(ID2, NAME2));
    }

    @Test
    void shouldGetById() {
        assertEquals(NAME1, underTest.getById(ID1).getName());
        assertEquals(NAME2, underTest.getById(ID2).getName());
    }

    @Test
    void shouldGetAll() {
        assertEquals(2, underTest.getAll().size());
    }

    @Test
    void shouldRemoveAll() {
        underTest.removeAll();
        assertEquals(0, underTest.getAll().size());
    }

    @Test
    void shouldRemoveById() {
        underTest.removeById(ID1);
        assertEquals(1, underTest.getAll().size());
        assertEquals(NAME2, underTest.getById(ID2).getName());
    }

    @Test
    void shouldSuccessivelyGenerateIds() {
        int id = underTest.generateId();
        assertTrue(id > 0);
        int nextId = underTest.generateId();
        assertEquals(id + 1, nextId);
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    private class TestEntity {
        private final int id;
        private final String name;

        @Override
        public int hashCode() {
            return id;
        }
    }

}
