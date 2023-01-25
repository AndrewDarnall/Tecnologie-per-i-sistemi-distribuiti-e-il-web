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
@RequestMapping("/updateBook")
public class UpdateBookController {

    private final BookRepository bookrepo;

    public UpdateBookController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getUpdateForm(Model model) {
        model.addAttribute("bbook", new Book());
        model.addAttribute("books",bookrepo.findAll());
        return "updateForm";
    }

    @PostMapping
    public String getUpdateResult(@ModelAttribute Book book, Model model) {
        
        // Need a proper way to handle possible delete mistakes!
        bookrepo.update(book);
        model.addAttribute("book", book);
        
        return "updateSuccess";
    }

}
//Can definitely do with a better insert mecchanism but I have been at itfor hours so this will do for now