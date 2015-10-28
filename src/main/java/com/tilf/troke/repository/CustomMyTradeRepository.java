package com.tilf.troke.repository;

import com.tilf.troke.entity.TransactionsEntity;

import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
public interface CustomMyTradeRepository {
    //Get les transactions qui sont prêts à êtres répondues
    List<TransactionsEntity> getPendingTransactionsByUserID(String userID);
    //Get les transactions qui ont été envoyés ou déjà répondu
    List<TransactionsEntity> getSentTransactionsByUserID(String userID);
    //Get les transactions qui ont été complétés
    List<TransactionsEntity> getCompletedTransactionsByUserID(String userID);
}
