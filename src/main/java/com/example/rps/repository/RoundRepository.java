package com.example.rps.repository;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class RoundRepository extends InMemoryRepository<RoundEntity>{

    public Collection<RoundEntity> findByUser(final String userName) {
        return getAll().stream()
                .filter(r -> userName.equals(r.getUser()))
                .collect(Collectors.toList());
    }

    public RoundEntity createRound() {
        var round = new RoundEntity();
        round.setId(generateId());
        return round;
    }
}
