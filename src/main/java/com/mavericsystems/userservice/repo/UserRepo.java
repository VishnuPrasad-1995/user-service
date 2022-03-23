package com.mavericsystems.userservice.repo;

import com.mavericsystems.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {
    Optional<User> findByEmail(String emailId);
}
