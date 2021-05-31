package com.example.rps.controller;

import com.example.rps.repository.RoundEntity;
import com.example.rps.repository.RoundRepository;
import com.example.rps.repository.UserEntity;
import com.example.rps.repository.UserRepository;
import com.example.rps.service.GameResult;
import com.example.rps.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoundRepository roundRepository;

    @PostMapping("/new")
    public ResponseEntity<SuccessResponse> newRound(UriComponentsBuilder uriBuilder) {
        var user = userRepository.findBySessionOrCreate(getSession());
        var game = gameService.playRound();
        var entity = saveResult(game, user.getName());

        SuccessResponse response = ResponseMapper.mapSuccess(List.of(entity));
        var location = uriBuilder.path("/api/games/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> byId(@PathVariable("id") final Integer id) {
        RoundEntity entity = roundRepository.getById(id);

        if (entity != null) {
            SuccessResponse response = ResponseMapper.mapSuccess(List.of(entity));
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getAll(@RequestParam(name = "AllUsers", required = false) final Boolean isAllUsers) {
        Collection<RoundEntity> entries;
        if (isAllUsers != null && isAllUsers) {
            entries = roundRepository.getAll();
        } else {
            UserEntity user = userRepository.findBySessionOrCreate(getSession());
            entries = roundRepository.findByUser(user.getName());
        }

        SuccessResponse response = ResponseMapper.mapSuccess(entries);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/all")
    public ResponseEntity<SuccessResponse> deleteAll(@RequestParam(name = "AllUsers", required = false) final Boolean isAllUsers) {
        if (isAllUsers != null && isAllUsers) {
            roundRepository.removeAll();
        } else {
            UserEntity user = userRepository.findBySessionOrCreate(getSession());
            Collection<RoundEntity> entries = roundRepository.findByUser(user.getName());
            entries.forEach(r -> roundRepository.removeById(r.getId()));
        }
        return ResponseEntity.ok().build();
    }

    private RoundEntity saveResult(final GameResult game, final String userName) {
        var entity = roundRepository.createRound();
        entity.setUser(userName);
        entity.setFirstPlayer(game.getFirstPlayer());
        entity.setSecondPlayer(game.getSecondPlayer());
        entity.setWinner(game.getWinner());

        roundRepository.add(entity);
        return entity;
    }

    private String getSession() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }
}
