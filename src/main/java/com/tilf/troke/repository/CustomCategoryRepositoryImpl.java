package com.tilf.troke.repository;

import com.tilf.troke.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Alex on 2015-11-20.
 */
@Repository
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryEntity> getAllCategory()
    {
        String query = "select t from CategoryEntity t";
        Query queryObject = entityManager.createQuery(query);
        List<CategoryEntity> listCat = (List<CategoryEntity>)queryObject.getResultList();
        return listCat;
    }
}
