package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-10-21.
 */
@Entity
@Table(name = "objectrating", schema = "", catalog = "troke")
public class ObjectratingEntity {
    private int idobjectrating;
    private int idobject;
    private Integer estvalue;

    @Id
    @Column(name = "IDOBJECTRATING")
    public int getIdobjectrating() {
        return idobjectrating;
    }

    public void setIdobjectrating(int idobjectrating) {
        this.idobjectrating = idobjectrating;
    }

    @Basic
    @Column(name = "IDOBJECT")
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Basic
    @Column(name = "ESTVALUE")
    public Integer getEstvalue() {
        return estvalue;
    }

    public void setEstvalue(Integer estvalue) {
        this.estvalue = estvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectratingEntity that = (ObjectratingEntity) o;

        if (idobjectrating != that.idobjectrating) return false;
        if (idobject != that.idobject) return false;
        if (estvalue != null ? !estvalue.equals(that.estvalue) : that.estvalue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idobjectrating;
        result = 31 * result + idobject;
        result = 31 * result + (estvalue != null ? estvalue.hashCode() : 0);
        return result;
    }
}
