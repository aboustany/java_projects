package com.org.BullsAndCows.dto;


import jakarta.validation.constraints.Pattern;

public class Guess {
    @Pattern(regexp = "\\d{4}", message = "Guess must be a 4-digit number.")
    private String guess;

    private Long gameId;

    public Guess() {}

    public Guess(String guess) {
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public Long getGameId() {
        return gameId;
    }

}
