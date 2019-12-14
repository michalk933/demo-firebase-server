package com.example.demofirebaseserver.demofirebaseserver.service;

import com.example.demofirebaseserver.demofirebaseserver.dto.Chat;
import com.example.demofirebaseserver.demofirebaseserver.dto.Message;
import com.example.demofirebaseserver.demofirebaseserver.repository.ChatRepository;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FirebaseService {

    private final static String DB_NAME = "chat";

    private final ChatRepository chatRepository;

    public FirebaseService(final ChatRepository chatRepository) throws IOException {
        this.chatRepository = chatRepository;

        FileInputStream serviceAccount =
                new FileInputStream("/Users/michalkuchciak/IdeaProjects/demo-firebase-client/src/main/resources/livedemo-5125e-firebase-adminsdk-6cfka-e157f86144.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://livedemo-5125e.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Transactional
    public void saveChat(final Chat chat) {
        Chat newChat = chatRepository.save(chat);
        FirestoreClient
                .getFirestore()
                .collection(DB_NAME)
                .document(newChat.getId())
                .set(newChat);
    }

    public void addNewMessage(final String chatId, final Message message) {
        Chat chat = getChatById(chatId);
        List<Message> messages = chat.getMessages();
        messages.add(message);
        chat.setMessages(messages);
        saveChat(chat);
    }

    public Chat getChatFromFirebase(final String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(DB_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Chat.class);
        } else {
            return new Chat();
        }
    }

    private Chat getChatById(final String id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ERROR!"));
    }

}
