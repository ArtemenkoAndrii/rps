package com.example.rps.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundEntity {
    private int id;
    private String user;
    private String firstPlayer;
    private String secondPlayer;
    private String winner;

    @Override
    public int hashCode() {
        return id;
    }
}
