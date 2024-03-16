package com.linkc.linkcbackend.repository;

import com.linkc.linkcbackend.domain.Box;
import com.linkc.linkcbackend.domain.BoxHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxHistoryRepository extends MongoRepository<BoxHistory, String> {
}
