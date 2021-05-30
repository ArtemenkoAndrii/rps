package com.example.rps.controller;

public class ResponseMapper {
    private static final String TYPE = "round";

    public static SuccessResponse mapSuccess() {
        return new SuccessResponse(null);
    }

    public static ErrorResponse mapError() {
        return new ErrorResponse(null);
    }
}
