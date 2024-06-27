package com.org.BullsAndCows.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String guess;
    private LocalDateTime guessTime;
    private String result;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public Round() {
    }

    public Round(Game game, String guess) {
        this.game = game;
        this.guess = guess;
        this.guessTime = LocalDateTime.now();
    }

    public Round(Game game, String guess, String result, LocalDateTime now) {
        this.game = game;
        this.guess = guess;
        this.result = result;
        this.guessTime = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
