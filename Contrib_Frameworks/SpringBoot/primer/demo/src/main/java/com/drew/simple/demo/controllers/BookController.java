package com.drew.simple.demo.controllers;

import java.util.ArrayList;
import java.util.List;

// Import the model
import com.drew.simple.demo.models.*;


// Import the controller and mapper annotations
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// important to import for the UI model connection
import org.springframework.ui.Model;
// The model interface is used for data exchange between model and view/controller
// it is a place for attribute changing and data place holding, this explains
// the scaffolding seen prior to the Data manipulation capabilities explored in 
// the Spring Boot lectures


// Lets define such class as a controller, formally with the annotations
// the mapping annotaion replaces the servlet's web.xml
// spolier alert, the Spring Boot framework uses servlets
@Controller
@RequestMapping("/books")
public class BookController {
    
    private static List<Book> books = new ArrayList<>();


    // adding a bok
    static {
        books.add(new Book("Percy Jackson e gli dei dell olimpo: il mare dei mostri","Rick Riordan",5,11.50));
    }


    // This annotation specifyes how the Spring Boot framework
    // catches and maps the GET request
    @GetMapping
    public String getAllBooks(Model model) {

        model.addAttribute("books", books);

        return "books";

    }

}

@RequestMapping("/error")
class ErrorHand {

    @GetMapping
    public void sayError() {
        System.err.println("BOY YOU DONE MESSED UP!");
    }

}

// This might be the only 'brains' of the whole operation, indeed from the MVC Structural Design Pattern
// the controller ... controls ... 