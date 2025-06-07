package com.example.Travel_mgmt_minor.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "group_chat_message")
public class GroupChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private String groupId;  // Should be String if DB has values like "1" and "send"

    private String message;

    private String sender;

    private LocalDateTime timestamp;

    // getters/setters
}



