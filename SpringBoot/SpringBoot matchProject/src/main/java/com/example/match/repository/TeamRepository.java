package com.example.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.match.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{}
