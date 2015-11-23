package com.tilf.troke.repository;

import com.tilf.troke.entity.SubcategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Alex on 2015-11-20.
 */
@Repository
public class CustomSubcategoryRepositoryImpl implements CustomSubcategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // retourne une liste de tout les sous category pour une category
    @Override
    public List<SubcategoryEntity> getAllSubCat(int categoryID) {
        String query = "select t from SubcategoryEntity t where t.idcategory= :IDcat";
        Query queryObject = entityManager.createQuery(query);
        queryObject.setParameter("IDcat", categoryID);
        List<SubcategoryEntity> listCat = (List<SubcategoryEntity>)queryObject.getResultList();
        return listCat;
    }
}
