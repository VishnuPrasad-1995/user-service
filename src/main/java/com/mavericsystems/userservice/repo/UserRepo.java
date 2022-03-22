package com.mavericsystems.userservice.repo;

import com.mavericsystems.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {
}
