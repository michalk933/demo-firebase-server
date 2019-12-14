package com.example.demofirebaseserver.demofirebaseserver.repository;

import com.example.demofirebaseserver.demofirebaseserver.dto.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
