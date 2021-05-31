package com.example.rps.controller;

import com.example.rps.repository.RoundEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseMapper {
    private static final String TYPE = "round";

    public static SuccessResponse mapSuccess(final Collection<RoundEntity> rounds) {
        List<Round> data = rounds.stream()
                .map(r -> new Round(TYPE, r.getId(), new Attributes(r.getUser(), r.getFirstPlayer(), r.getSecondPlayer(), r.getWinner())))
                .collect(Collectors.toList());

        return new SuccessResponse(data);
    }

    public static ErrorResponse mapError(final String detail) {
        return new ErrorResponse(List.of(new Error(detail)));
    }
}
