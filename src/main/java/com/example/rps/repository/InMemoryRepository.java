package com.example.rps.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRepository<T> {
    private final Map<Integer, T> entities = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public void add(T entity) {
        entities.putIfAbsent(entity.hashCode(), entity);
    }

    public T getById(Integer id) {
        return entities.get(id);
    }

    public Collection<T> getAll() {
        return entities.values();
    }

    public void removeAll() {
        entities.clear();
    }

    public T removeById(Integer id) {
        return entities.remove(id);
    }

    public int generateId() {
        return idGenerator.getAndIncrement();
    }
}
