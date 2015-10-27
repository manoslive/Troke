package com.tilf.troke.repository;

/**
 * Created by Shaun Cooper on 2015-10-27.
 */
public interface CustomChatMessageRepository {
    String getChatLogByTransactionID(int tradeID);
}
