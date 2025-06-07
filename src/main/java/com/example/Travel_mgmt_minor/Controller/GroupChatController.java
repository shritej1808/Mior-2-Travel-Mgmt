package com.example.Travel_mgmt_minor.Controller;

import com.example.Travel_mgmt_minor.Entity.GroupChatMessage;
import com.example.Travel_mgmt_minor.Repository.GroupChatMessageRepository;
import com.example.Travel_mgmt_minor.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class GroupChatController {

    @Autowired
    private GroupChatMessageRepository messageRepo;

    // ✅ Send message (groupId from URL)
    @PostMapping("/{groupId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> sendMessage(@PathVariable String groupId,
                                         @RequestBody ChatMessageDTO chatMessage,
                                         Authentication auth) {
        GroupChatMessage msg = new GroupChatMessage();
        msg.setGroupId(groupId);
        msg.setSender(auth.getName());
        msg.setMessage(chatMessage.getMessage());
        msg.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(messageRepo.save(msg));
    }

    // ✅ Get messages by groupId
    @GetMapping("/{groupId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<GroupChatMessage>> getMessages(@PathVariable String groupId) {
        List<GroupChatMessage> messages = messageRepo.findByGroupIdOrderByTimestampAsc(groupId);
        System.out.println("Fetched " + messages.size() + " messages for groupId: " + groupId);
        return ResponseEntity.ok(messages);
    }

}
