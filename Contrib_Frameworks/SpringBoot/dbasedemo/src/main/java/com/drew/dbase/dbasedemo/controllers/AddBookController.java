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
@RequestMapping("/addBook")
public class AddBookController {
    
    private final BookRepository bookrepo;

    public AddBookController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String insertFrom(Model model) {
        // This ties the attribute for the insertion, and returns the newly created object, almost like magic!
        model.addAttribute("book", new Book());
        // This returns the template name mapped to a request
        return "bookAdder";
    }

    // The ModelAttribute annotation does quite some complex configuration
    // Annotations are the best and better alternatives to the manual
    // XML configuration
    @PostMapping
    public String bookSubmit(@ModelAttribute Book book, Model model) {
        // Executes the insertion query
        bookrepo.save(book);
        // bind the attribute
        model.addAttribute("book", book);
        // return the model to show, we could show the added student or simply
        // return to the main page

        // when the template is returned, the bound model is also, with it
        return "insertionSuccess";
    }

}