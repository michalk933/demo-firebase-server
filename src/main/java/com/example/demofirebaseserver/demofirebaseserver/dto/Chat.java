package com.example.demofirebaseserver.demofirebaseserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private String id;

    private String sender;

    private String recipient;

    private List<Message> messages = new ArrayList<>();

}
