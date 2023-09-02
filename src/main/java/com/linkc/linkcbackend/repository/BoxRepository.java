package com.linkc.linkcbackend.repository;

import com.linkc.linkcbackend.domain.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends MongoRepository<Box, String> {
    @Query("{reservedBy:'?0'}")
    List<Box> findBoxReservedByUserId(String id);
}
