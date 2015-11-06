package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.TransactionsEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Repository
public class CustomMyTradeRepositoryImpl implements CustomMyTradeRepository{
    @PersistenceContext
    private EntityManager entityManager;

    //Get les transactions qui sont prêts à êtres répondues
    public List<TransactionsEntity> getPendingTransactionsByUserID(String userID){
        String query = "select t from TransactionsEntity t where t.iscompleted = 'F' and t.turn = :userId order by t.datetransaction desc";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userId", userID);
        List<TransactionsEntity> listTransactions = (List<TransactionsEntity>)queryObject.getResultList();
        return listTransactions;
    }
    //Get les transactions qui ont été envoyés ou déjà répondu
    public List<TransactionsEntity> getSentTransactionsByUserID(String userID){
        String query = "select t from TransactionsEntity t where t.iscompleted = 'F' and t.turn != :userId and (t.iduser1 = :userId or t.iduser2 = :userId) order by t.datetransaction desc";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userId", userID);
        List<TransactionsEntity> listTransactions = (List<TransactionsEntity>)queryObject.getResultList();
        return listTransactions;
    }
    //Get les transactions qui ont été complétés
    public List<TransactionsEntity> getCompletedTransactionsByUserID(String userID){
        String query = "select t from TransactionsEntity t where t.iscompleted = 'T' and (t.iduser1 = :userId or t.iduser2 = :userId) order by t.datetransaction desc";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("userId", userID);
        List<TransactionsEntity> listTransactions = (List<TransactionsEntity>)queryObject.getResultList();
        return listTransactions;
    }
}
