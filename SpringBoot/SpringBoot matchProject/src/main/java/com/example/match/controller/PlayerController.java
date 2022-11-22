package com.example.match.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.match.domain.*;
import com.example.match.service.*;

@RestController
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping("/player")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> entityList = service.getAllPlayers();
        return ResponseEntity.ok(entityList);
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable long id) {
        Optional<Player> entity = service.getPlayer(id);
        if(entity.isPresent())
            return ResponseEntity.ok(entity.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/playerName/{name}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String name){
        Optional<Player> entity = service.getByName(name);
        if(entity.isPresent())
            return ResponseEntity.ok(entity.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/player")
    public ResponseEntity<Player> addPlayer(@Valid @RequestBody Player e) throws URISyntaxException {
        if (e.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Player entity = service.addPlayer(e);
        return ResponseEntity.created(new URI("/api/player" + entity.getId())).body(entity);
    }

    @PutMapping("/player")
    public ResponseEntity<Player> updatePlayer(@Valid @RequestBody Player e) {
        if (e.getId() == null)
            return ResponseEntity.notFound().build();
        Player entity = service.updatePlayer(e);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/player/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable long id) {
        if (service.getPlayer(id).isEmpty())
            return ResponseEntity.notFound().build();

        service.deletePlayer(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/player")
    public ResponseEntity<Void> deleteAllPlayers(){
        List<Player> playerList = service.getAllPlayers();
        playerList.forEach((p)-> service.deletePlayer(p));
        return ResponseEntity.ok().build();
    }
}