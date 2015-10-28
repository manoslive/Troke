package com.tilf.troke.repository;

import com.tilf.troke.entity.ChatmessageEntity;

/**
 * Created by Shaun Cooper on 2015-10-27.
 */
public interface CustomChatMessageRepository {
    ChatmessageEntity getChatLogByTransactionID(int tradeID);
}
