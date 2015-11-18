package com.tilf.troke.repository;

import com.tilf.troke.entity.TransactionmoneyEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Shaun Cooper on 2015-11-18.
 */
@Repository
public class CustomTransactionMoneyRepositoryImpl implements CustomTransactionMoneyRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public TransactionmoneyEntity getTransactionMoney(int transactionID, String user){
        String query = "select * from transactionmoney t where t.IDUSER = :iduser and t.idtransaction = :idtransaction";
        Query queryObject = entityManager.createNativeQuery(query, TransactionmoneyEntity.class);
        queryObject.setParameter("iduser", user);
        queryObject.setParameter("idtransaction", transactionID);

        TransactionmoneyEntity transactionMoneyEntityEntity = (TransactionmoneyEntity) queryObject.getSingleResult();

        return transactionMoneyEntityEntity;
    }
}
