package com.example.examproject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String location;

    private String date;

    private String description;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setLocation(String id) {
        this.location = id;
    }

    public String getLocation() {
        return location;
    }

    public void setDate(String id) {
        this.date = id;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String id) {
        this.description = id;
    }

    public String getDescription() {
        return description;
    }
}