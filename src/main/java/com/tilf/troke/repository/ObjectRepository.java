package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 2015-10-23.
 */
@Repository
public interface ObjectRepository extends JpaRepository<ObjectsEntity, String> {

    ObjectsEntity save(ObjectsEntity object) ;



}
