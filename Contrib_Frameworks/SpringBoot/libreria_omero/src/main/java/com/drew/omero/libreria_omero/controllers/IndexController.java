package com.drew.omero.libreria_omero.controllers;

import com.drew.omero.libreria_omero.data.BookRepository;
import com.drew.omero.libreria_omero.models.Bbooks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class IndexController {

    private final BookRepository bookrepo;

    
    public IndexController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("suggestions", bookrepo.getSuggestion());
        return "index";
    }

}