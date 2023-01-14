package com.drew.modelplayer.controllers;

// First we import the model
import com.drew.modelplayer.models.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import java.util.List;
import java.util.ArrayList;

//@RestController
@Controller
@RequestMapping("/students")
public class StudentController {
 
    private static List<Student> students = new ArrayList<>();

    // Defining a static block
    static {
        students.add(new Student(1,"Percy Jackson"));
    }
    
    @GetMapping
    public String getAllStudents(Model model) {

        model.addAttribute("students",students);
        // Returns the name of the template, which is tagged to be properly rendered later
        return "students";
    }

}

/**
 * But what is a controller class ?
 * 
 * In Spring Boot, the controller class is responsible for 
 * processing incoming REST API requests, preparing a model, 
 * and returning the view to be rendered as a response. 
 * The controller classes in Spring are annotated either by the 
 * @Controller or the @RestController annotation.
 * 
 */