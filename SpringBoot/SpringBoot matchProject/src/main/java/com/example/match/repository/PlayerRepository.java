package com.example.match.repository;

import java.util.List;
import java.util.Optional;

import com.example.match.domain.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{
    Optional<Player> findByName(String name);
    List<Player> findByTeamId(Long id);
}
