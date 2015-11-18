package com.tilf.troke.repository;

import com.tilf.troke.entity.ChatmessageEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Shaun Cooper on 2015-10-27.
 */
@Repository
public class CustomChatMessageRepositoryImpl implements CustomChatMessageRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public ChatmessageEntity getChatLogByTransactionID(int tradeID){
        String query = "select cm from ChatmessageEntity cm where cm.idchat = (select c.idchat from ChatEntity c where c.idtransaction = :idTransaction)";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("idTransaction", tradeID);
        ChatmessageEntity chatLog = (ChatmessageEntity) queryObject.getSingleResult();
        return chatLog;
    }
}
