package com.example.rps.repository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public class UserRepository extends InMemoryRepository<UserEntity> {
    private static final String USER = "User ";

    public UserEntity findBySessionOrCreate(final String session) {
        Optional<UserEntity> user = findBySession(session);
        return user.orElseGet(() -> generateUser(session));
    }

    private Optional<UserEntity> findBySession(final String session) {
        return getAll().stream()
                .filter(userEntity -> session.equals(userEntity.getSession()))
                .findFirst();
    }

    private UserEntity generateUser(final String session) {
        UserEntity user = new UserEntity();
        user.setId(generateId());
        user.setSession(session);
        user.setName(USER + user.getId());

        add(user);

        return user;
    }

}
