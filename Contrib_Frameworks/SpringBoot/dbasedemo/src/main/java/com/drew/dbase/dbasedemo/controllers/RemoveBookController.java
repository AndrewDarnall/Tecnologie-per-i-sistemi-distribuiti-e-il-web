package com.drew.dbase.dbasedemo.controllers;

import com.drew.dbase.dbasedemo.data.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.drew.dbase.dbasedemo.models.Book;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/deleteBook")
public class RemoveBookController {
    
    private final BookRepository bookrepo;

    public RemoveBookController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getDeletePage(Model model) {
        model.addAttribute("bbook",new Book());
        return "deletePage";
    }

    @PostMapping
    public String getDeleteStatusPage(@ModelAttribute Book book, Model model) {
        bookrepo.remove(book);
        return "deleteSuccess";
    }

}