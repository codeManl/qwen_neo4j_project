package com.example.graph.repository;

import com.example.graph.PersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode, String> {
    PersonNode findByName(String name);
}