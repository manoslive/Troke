package com.tilf.troke.repository;

import com.tilf.troke.entity.ChatmessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Repository
public interface ChatmessageRepository extends JpaRepository<ChatmessageEntity, String>{
    ChatmessageEntity save(ChatmessageEntity chatmessageEntity);
}
