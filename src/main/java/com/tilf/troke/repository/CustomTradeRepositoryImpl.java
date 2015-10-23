package com.tilf.troke.repository;

import com.tilf.troke.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-21.
 */
@Repository
public class CustomTradeRepositoryImpl implements CustomTradeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addNewTrade(String User1, String User2, List<Integer> objects, String chatLog)
    {
        TransactionsEntity newTransaction = new TransactionsEntity();
        newTransaction.setIduser1(User1);
        newTransaction.setIduser2(User1);
        newTransaction.setDatetransaction(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newTransaction.setTurn(User2);
        newTransaction.setIscompleted("N");

        String query = "select o.IDTRANSACTION from transactions o where o.IDUSER1 = :idUser1 and o.IDUSER2 = :idUser2";
        Query queryObject = entityManager.createNativeQuery(query);
        queryObject.setParameter("idUser1", User1);
        queryObject.setParameter("idUser2", User2);
        int idTransaction = (int)queryObject.getSingleResult();

        ChatEntity newChat = new ChatEntity();
        newChat.setIdtransaction(idTransaction);  ////////pas mis le id

        String query2 = "select o.IDCHAT from chat o where o.IDTRANSACTION = :idTransaction";
        Query queryObject2 = entityManager.createNativeQuery(query2);
        queryObject2.setParameter("idTransaction", User1);
        int idChat = (int)queryObject.getSingleResult();

        ChatmessageEntity newChatMessage = new ChatmessageEntity();
        newChatMessage.setIdchat(idChat);
        newChatMessage.setDateTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newChatMessage.setMsg(chatLog);


        for (Iterator<Integer> obj = objects.iterator(); obj.hasNext(); ){
            Integer currentObject = obj.next();
            ObjecttransactionEntity addTransactionsObjects = new ObjecttransactionEntity();
            addTransactionsObjects.setIdobject(currentObject);
            addTransactionsObjects.setIdtransaction(idTransaction);
        }
    }
}
