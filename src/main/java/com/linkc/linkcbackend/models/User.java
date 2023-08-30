package com.linkc.linkcbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("test")
public record User(
        @Id
        String id,
        String username,
        String password
) { }
