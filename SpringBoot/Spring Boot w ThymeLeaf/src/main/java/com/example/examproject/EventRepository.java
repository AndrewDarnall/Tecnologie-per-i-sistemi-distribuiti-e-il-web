package com.example.examproject;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface EventRepository extends CrudRepository<Event, Integer> {



}
