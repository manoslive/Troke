package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjecttransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Repository
public interface ObjectsTransactionRepository extends JpaRepository<ObjecttransactionEntity, String>{
    ObjecttransactionEntity save(ObjecttransactionEntity objecttransactionEntity);
}
