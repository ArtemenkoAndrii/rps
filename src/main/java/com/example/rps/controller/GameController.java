package com.example.rps.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @PostMapping("/new")
    public ResponseEntity<SuccessResponse> newRound(UriComponentsBuilder uriBuilder) {
        SuccessResponse response = ResponseMapper.mapSuccess();
        var location = uriBuilder.path("/api/games/{id}").buildAndExpand().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> byId(@PathVariable("id") final Integer id) {
        SuccessResponse response = ResponseMapper.mapSuccess();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getAll(@RequestParam(name = "AllUsers", required = false) final Boolean isAllUsers) {
        SuccessResponse response = ResponseMapper.mapSuccess();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/all")
    public ResponseEntity<SuccessResponse> deleteAll(@RequestParam(name = "AllUsers", required = false) final Boolean isAllUsers) {
        return ResponseEntity.ok().build();
    }

}
