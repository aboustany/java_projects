package com.org.BullsAndCows.dao;

import com.org.BullsAndCows.dto.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByGameIdOrderByGuessTimeAsc(Long gameId);
}
