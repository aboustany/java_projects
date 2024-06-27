package com.org.BullsAndCows.dto;

import com.org.BullsAndCows.service.GameService;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    private String answer;
    public String gameStatus;

    @OneToMany(mappedBy = "game")
    private List<Round> rounds;

    public Game() {
        this.answer = GameService.generateUniqueDigits();
        this.gameStatus = "IN_PROGRESS";
    }

    public Long getId() {
        return gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setGameStatus(String status) {
        this.gameStatus = status;
    }



}
