package com.example.rps.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private int id;
    private String name;
    private String session;

    @Override
    public int hashCode() {
        return id;
    }
}
