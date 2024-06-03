package com.example.user.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user.models.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    void deleteByUsername(String string);

}
