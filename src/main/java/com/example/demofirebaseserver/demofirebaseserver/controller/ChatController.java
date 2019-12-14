package com.example.demofirebaseserver.demofirebaseserver.controller;

import com.example.demofirebaseserver.demofirebaseserver.dto.Chat;
import com.example.demofirebaseserver.demofirebaseserver.dto.Message;
import com.example.demofirebaseserver.demofirebaseserver.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final FirebaseService firebaseService;

    @PostMapping("/create/chat")
    public void saveChat(@RequestBody Chat chat) {
        firebaseService.saveChat(chat);
    }

    @PostMapping("/add/message")
    public void saveMessage(
            @RequestParam("chatId") String chatId,
            @RequestBody Message message) {
        firebaseService.addNewMessage(chatId, message);
    }

    @GetMapping
    public Chat get() throws ExecutionException, InterruptedException {
        return firebaseService.getChatFromFirebase("5df54b313d88382cfdf2df9a");
    }


}
