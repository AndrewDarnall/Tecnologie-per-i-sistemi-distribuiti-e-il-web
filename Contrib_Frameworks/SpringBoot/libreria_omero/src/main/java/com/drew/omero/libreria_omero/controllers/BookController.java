package com.drew.omero.libreria_omero.controllers;

import com.drew.omero.libreria_omero.models.Bbooks;
import com.drew.omero.libreria_omero.data.BookRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookRepository bookrepo;

    public BookController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("books", bookrepo.findAll());
        model.addAttribute("count", bookrepo.count());
        return "books"; // Name of the template
    }

    @GetMapping(path="/searchByName/{title}")
    public String getBookByName(Model model,@PathVariable String title) {
        model.addAttribute("books", bookrepo.findByTitle(title));
        return "books"; // Name of the template
    }

}

@Controller
@RequestMapping("/addBook")
class AdderController {

    private final BookRepository bookrepo;

    public AdderController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getForm(Model model) {
        model.addAttribute("book", new Bbooks());
        return "bookAdder";
    }

    @PostMapping
    public String addBook(@ModelAttribute Bbooks book) {
        bookrepo.save(book);
        // Perform a redirect instead of showing the success
        return "redirect:/books";
    }

}


@Controller
@RequestMapping("/logout")
class LogoutController {

    @GetMapping
    public String getLogout() {
        return "logout";
    }

}

@Controller
@RequestMapping("/updateBook")
class BookUpdateController {

    private BookRepository bookrepo;

    public BookUpdateController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getUpdateForm(Model model) {
        model.addAttribute("books", bookrepo.findAll());
        model.addAttribute("bbook", new Bbooks());
        return "updateForm";
    }

    @PostMapping
    public String updateBook(@ModelAttribute Bbooks book) {
        bookrepo.update(book.getId(),book.getTitle(),book.getAuthor(),book.getRating(),book.getPrice());
        return "redirect:/books";
    }

}

@Controller
@RequestMapping("/deleteBook")
class BookDeleteController {

    private BookRepository bookrepo;

    public BookDeleteController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getUpdateForm(Model model) {
        model.addAttribute("books", bookrepo.findAll());
        model.addAttribute("bbook", new Bbooks());
        return "deletePage";
    }

    @PostMapping
    public String removeRecord(@ModelAttribute Bbooks book) {
        bookrepo.remove(book.getId());
        return "redirect:/books";
    }

}

@Controller
@RequestMapping("/searchBook")
class SearchController {

    private BookRepository bookrepo;

    public SearchController(BookRepository bookrepo) {
        this.bookrepo = bookrepo;
    }

    @GetMapping
    public String getSearchPage(Model model) {
        model.addAttribute("bbook",new Bbooks());
        return "searchPage";
    }

    @PostMapping
    public String getSearchedPage(@ModelAttribute Bbooks book, Model model) {
        
        model.addAttribute("bbook", new Bbooks());
        model.addAttribute("books",bookrepo.search(book.getTitle()));
        return "searchPageResults";

    }

}

/**
 * 
 * All CRUD operations succesfully converted from JDBC to JPA!
 * 
 */