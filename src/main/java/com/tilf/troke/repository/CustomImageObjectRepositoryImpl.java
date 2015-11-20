package com.tilf.troke.repository;

import com.tilf.troke.entity.ImageobjectEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Alex on 2015-11-19.
 */
@Repository
public class CustomImageObjectRepositoryImpl implements CustomImageObjectRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ImageobjectEntity> getImageObjectbyObjectId(int objectID) {
        String query = "select t from ImageobjectEntity t where t.idobject = :objectID order by ismain";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("objectID", objectID);
        List<ImageobjectEntity> listObject = (List<ImageobjectEntity>)queryObject.getResultList();
        return listObject;

    }
}
