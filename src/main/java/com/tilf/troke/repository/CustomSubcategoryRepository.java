package com.tilf.troke.repository;

import com.tilf.troke.entity.SubcategoryEntity;

import java.util.List;

/**
 * Created by Alex on 2015-11-20.
 */
public interface CustomSubcategoryRepository {

    List<SubcategoryEntity> getAllSubCat(int categoryID);
}
