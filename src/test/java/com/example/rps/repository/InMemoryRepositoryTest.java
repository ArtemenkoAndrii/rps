package com.example.rps.repository;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryRepositoryTest {
    private static final int ID = 1;
    private static final String NAME = "Name";

    private final InMemoryRepository<TestEntity> underTest = new InMemoryRepository<>();

    @Test
    void shouldAddAndGet() {
        var newEntity = new TestEntity(ID, NAME);
        underTest.add(newEntity);
        var result = underTest.getById(newEntity.getId());
        assertEquals(newEntity, result);
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
