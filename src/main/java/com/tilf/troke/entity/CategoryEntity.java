package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "category", schema = "", catalog = "troke")
public class CategoryEntity {
    private int idcategory;
    private String nameCategory;

    @Id
    @Column(name = "IDCATEGORY")
    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    @Basic
    @Column(name = "NAME_CATEGORY")
    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (idcategory != that.idcategory) return false;
        if (nameCategory != null ? !nameCategory.equals(that.nameCategory) : that.nameCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idcategory;
        result = 31 * result + (nameCategory != null ? nameCategory.hashCode() : 0);
        return result;
    }
}
