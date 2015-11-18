package com.tilf.troke.repository;

import com.tilf.troke.entity.TransactionmoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaun Cooper on 2015-11-18.
 */
@Repository
public interface TransactionMoneyRepository  extends JpaRepository<TransactionmoneyEntity, String> {
    //TransactionmoneyEntity save(TransactionmoneyEntity tradeMoneyEntity);
}
