package com.linkc.linkcbackend.repository;

import com.linkc.linkcbackend.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNumber(String number);
    Optional<User> deleteByNumber(String number);
}
