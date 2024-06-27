package com.org.BullsAndCows.dao;

import com.org.BullsAndCows.dto.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
