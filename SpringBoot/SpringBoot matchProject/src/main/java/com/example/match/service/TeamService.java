package com.example.match.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.match.domain.*;
import com.example.match.repository.*;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;

    public Team addTeam(Team e) {
        return repository.save(e);
    }

    public Optional<Team> getTeam(Long id) {
        return repository.findById(id);
    }

    public List<Team> getAllTeams() {
        List<Team> output = new ArrayList<Team>();
        repository.findAll().forEach(output::add);
        return output;
    }

    public Team updateTeam(Team e) {
        return repository.save(e);
    }

    public void deleteTeam(Team e) {
        repository.delete(e);
}

    public void deleteTeam(Long id) {
        repository.deleteById(id);
    }
}
