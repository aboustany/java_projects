package com.org.BullsAndCows.controller;

import com.org.BullsAndCows.dto.Game;
import com.org.BullsAndCows.dto.Guess;
import com.org.BullsAndCows.dto.Round;
import com.org.BullsAndCows.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/begin")
    public ResponseEntity<Game> createGame() {
        Game game = gameService.createGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.findGameById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/game")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> makeGuess(@Valid @RequestBody Guess guess) {
        Round round = gameService.makeGuess(guess.getGameId(), guess.getGuess());
        return ResponseEntity.status(HttpStatus.CREATED).body(round);
    }

    @GetMapping("/rounds/{id}")
    public ResponseEntity<List<Round>> getRoundsForGame(@PathVariable Long id) {
        List<Round> rounds = gameService.getRoundsForGame(id);
        if (rounds.isEmpty()) {
            // TODO: handle the case where the game exists but has no rounds
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rounds);
    }




}
