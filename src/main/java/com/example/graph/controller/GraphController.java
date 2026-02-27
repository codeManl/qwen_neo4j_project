package com.example.graph.controller;

import com.example.graph.PersonNode;
import com.example.graph.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/persons")
    public List<PersonNode> getAllPersons() {
        Iterable<PersonNode> personsIterable = personRepository.findAll();
        List<PersonNode> personsList = new ArrayList<>();
        personsIterable.forEach(personsList::add);
        return personsList;
    }

    @GetMapping("/persons/{name}")
    @Cacheable(value = "graph-cache", key = "#name")
    public ResponseEntity<PersonNode> getPersonByName(@PathVariable String name) {
        PersonNode person = personRepository.findByName(name);
        return Optional.ofNullable(person)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/persons")
    @CacheEvict(value = "graph-cache", key = "#person.name")
    public PersonNode createPerson(@RequestBody PersonNode person) {
        return personRepository.save(person);
    }

    @PostMapping("/persons/{name}/friends/{friendName}")
    @CacheEvict(value = "graph-cache", key = "#name")
    public ResponseEntity<PersonNode> addFriend(
            @PathVariable String name,
            @PathVariable String friendName) {
        
        PersonNode person = personRepository.findByName(name);
        PersonNode friend = personRepository.findByName(friendName);
        
        if (person != null && friend != null) {
            person.addFriend(friend);
            PersonNode savedPerson = personRepository.save(person);
            return ResponseEntity.ok(savedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}