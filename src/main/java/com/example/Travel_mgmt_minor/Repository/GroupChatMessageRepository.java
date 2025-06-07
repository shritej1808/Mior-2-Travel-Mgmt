package com.example.Travel_mgmt_minor.Repository;


import com.example.Travel_mgmt_minor.Entity.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long> {
    List<GroupChatMessage> findByGroupIdOrderByTimestampAsc(String groupId);
}

