package com.example.graph;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Person")
public class PersonNode {

    @Id
    private String name;
    
    @Property("email")
    private String emailAddress;
    
    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<PersonNode> friends = new HashSet<>();
    
    public PersonNode() {}
    
    public PersonNode(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<PersonNode> getFriends() {
        return friends;
    }

    public void setFriends(Set<PersonNode> friends) {
        this.friends = friends;
    }
    
    public void addFriend(PersonNode friend) {
        this.friends.add(friend);
        friend.getFriends().add(this);
    }
}