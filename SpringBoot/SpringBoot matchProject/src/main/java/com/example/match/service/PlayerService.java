package com.example.match.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.match.domain.*;
import com.example.match.repository.*;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    public Player addPlayer(Player e) {
        return repository.save(e);
    }

    public Optional<Player> getPlayer(Long id) {
        return repository.findById(id);
    }

    public Optional<Player> getByName(String name){
        return repository.findByName(name);
    }

    public List<Player> getByTeamId(Long id){
        return repository.findByTeamId(id);
    }

    public List<Player> getAllPlayers() {
        List<Player> output = new ArrayList<Player>();
        repository.findAll().forEach(output::add);
        return output;
    }

    public Player updatePlayer(Player e) {
        return repository.save(e);
    }

    public void deletePlayer(Player e) {
        repository.delete(e);
}

    public void deletePlayer(Long id) {
        repository.deleteById(id);
    }
}
