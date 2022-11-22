/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.games.games;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author kylon
 */
@Controller
@RequestMapping("/gamedb")
public class gamedbCtrl {
    @Autowired
    private gamedbRepo gamedbRepo;
   
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }
    
    @PostMapping("/add")
    public ResponseEntity<game> addGame(@RequestBody game g) throws URISyntaxException {
        game ret = gamedbRepo.save(g);
        
        return ResponseEntity.created(new URI("/game/"+g.getId())).body(ret);
    }
    
    @GetMapping("/game/{id}")
    public ResponseEntity<Optional<game>> getGameById(@PathVariable Long id) {
        Optional g = gamedbRepo.findById(id);
        
        if (!g.isPresent())
            return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok().body(g);
    }
    
    @GetMapping("/list")
    public ResponseEntity<Iterable<game>> listGames() {
        return ResponseEntity.ok().body(gamedbRepo.findAll());
    }
    
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delGame(@PathVariable Long id) {
        gamedbRepo.deleteById(id);
        
        return ResponseEntity.ok().body(id+" deleted");
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
