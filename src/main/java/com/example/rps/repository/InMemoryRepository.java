package com.example.rps.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> {
    private final Map<Integer, T> entities = new ConcurrentHashMap<>();

    public void add(T entity) {
        entities.putIfAbsent(entity.hashCode(), entity);
    }

    public T getById(Integer id) {
        return entities.get(id);
    }

    public Collection<T> getAll() {
        return entities.values();
    }
}
