package com.drew.dbase.dbasedemo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.drew.dbase.dbasedemo.data.BookRepository;

// Need to add the data components and the repositories

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.drew.dbase.dbasedemo.models.Book;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/blog")
public class BookController {
   
    private final BookRepository bookrepo;

    public BookController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getAllBooks(Model model) {

        model.addAttribute("books", bookrepo.findAll());
        model.addAttribute("count", bookrepo.count());
        // Remember, the name of the tempalte to use needs to be returned
        // so that Spring Boot knows just what to use
        return "blog";
    }

}