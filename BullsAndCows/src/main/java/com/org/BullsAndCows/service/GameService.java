package com.org.BullsAndCows.service;

import com.org.BullsAndCows.dao.GameRepository;
import com.org.BullsAndCows.dao.RoundRepository;
import com.org.BullsAndCows.dto.Game;
import com.org.BullsAndCows.dto.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;

    @Autowired
    public GameService(GameRepository gameRepository, RoundRepository roundRepository) {
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
    }

    public Game createGame() {
        Game game = new Game();
        gameRepository.save(game);
        return game;
    }

    public List<Game> getAllGames() {
        List<Game> games = gameRepository.findAll();
        games.forEach(game -> {
            if ("IN_PROGRESS".equals(game.getGameStatus())) {
                game.setAnswer(null);
            }
        });
        return games;
    }

    public Optional<Game> findGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Round makeGuess(Long gameId, String guess) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        String result = evaluateGuess(guess, game.getAnswer());
        if (result.equals("4e:0p:")) {
            game.setGameStatus("FINISHED");
            gameRepository.save(game);
        }
        Round round = new Round(game, guess, result, LocalDateTime.now());
        return roundRepository.save(round);
    }

    public List<Round> getRoundsForGame(Long gameId) {
        return roundRepository.findByGameIdOrderByGuessTimeAsc(gameId);
    }



    public static String generateUniqueDigits(){
        boolean[] usedDigit = new boolean[10];
        StringBuilder result = new StringBuilder();
        Random r = new Random();

        while(result.length() < 4){
            int digit = r.nextInt(9);
            if (!usedDigit[digit]) {
                usedDigit[digit] = true;
                result.append(digit);
            }
        }
        return result.toString();
    }

    public String evaluateGuess(String guess, String answer) {
        int exactMatches = 0;
        int partialMatches = 0;

        for (int i = 0; i < guess.length(); i++) {
            char g = guess.charAt(i);
            if (g == answer.charAt(i)) {
                exactMatches++;
            } else if (answer.contains(String.valueOf(g))) {
                partialMatches++;
            }
        }

        return String.format("e:%d:p:%d", exactMatches, partialMatches);
    }
}
