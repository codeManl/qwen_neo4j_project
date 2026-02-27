package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user-cache", key = "#id")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @CachePut(value = "user-cache", key = "#user.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "user-cache", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable(value = "user-cache", key = "#email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}