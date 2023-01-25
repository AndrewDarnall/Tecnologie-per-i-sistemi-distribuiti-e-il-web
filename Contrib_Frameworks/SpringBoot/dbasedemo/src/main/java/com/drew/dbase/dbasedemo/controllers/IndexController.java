package com.drew.dbase.dbasedemo.controllers;

import com.drew.dbase.dbasedemo.data.BookRepository;
import com.drew.dbase.dbasedemo.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    
    private final BookRepository bookrepo;

    public IndexController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getRandomSelection(Model model) {

        model.addAttribute("selections", bookrepo.getSelection());
        return "index";
        
    }

}