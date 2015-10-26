package com.tilf.troke.repository;

import com.tilf.troke.entity.ChatEntity;
import com.tilf.troke.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, String> {
    ChatEntity save(ChatEntity chatEntity);
}
