package com.example.demofirebaseserver.demofirebaseserver.repository;

import com.example.demofirebaseserver.demofirebaseserver.dto.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
