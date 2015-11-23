package com.tilf.troke.entity;

import java.util.List;

/**
 * Created by Alex on 2015-11-23.
 */
public class CustomCategorySubCategoryEntity {
    private CategoryEntity category;

    public List<SubcategoryEntity> getListSubCat() {
        return listSubCat;
    }

    private List<SubcategoryEntity> listSubCat;


    public void setListSubCat(List<SubcategoryEntity> listSubCat) {
        this.listSubCat = listSubCat;
    }



    public CustomCategorySubCategoryEntity() {
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
