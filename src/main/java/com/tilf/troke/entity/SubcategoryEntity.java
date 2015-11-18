package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "subcategory", schema = "", catalog = "troke")
public class SubcategoryEntity {
    private int idSubcategory;
    private String nameSubcategory;
    private int idcategory;

    @Id
    @Column(name = "idSUBCATEGORY")
    public int getIdSubcategory() {
        return idSubcategory;
    }

    public void setIdSubcategory(int idSubcategory) {
        this.idSubcategory = idSubcategory;
    }

    @Basic
    @Column(name = "NAME_SUBCATEGORY")
    public String getNameSubcategory() {
        return nameSubcategory;
    }

    public void setNameSubcategory(String nameSubcategory) {
        this.nameSubcategory = nameSubcategory;
    }

    @Basic
    @Column(name = "IDCATEGORY")
    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubcategoryEntity that = (SubcategoryEntity) o;

        if (idSubcategory != that.idSubcategory) return false;
        if (idcategory != that.idcategory) return false;
        if (nameSubcategory != null ? !nameSubcategory.equals(that.nameSubcategory) : that.nameSubcategory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSubcategory;
        result = 31 * result + (nameSubcategory != null ? nameSubcategory.hashCode() : 0);
        result = 31 * result + idcategory;
        return result;
    }
}
