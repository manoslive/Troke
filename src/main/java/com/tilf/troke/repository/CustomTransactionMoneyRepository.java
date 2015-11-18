package com.tilf.troke.repository;

import com.tilf.troke.entity.TransactionmoneyEntity;

/**
 * Created by Shaun Cooper on 2015-11-18.
 */

public interface CustomTransactionMoneyRepository {
    TransactionmoneyEntity getTransactionMoney(int transactionID, String user);
}
