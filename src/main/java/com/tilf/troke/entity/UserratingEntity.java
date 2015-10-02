package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Manu on 2015-10-02.
 */
@Entity
@Table(name = "userrating", schema = "", catalog = "troke")
public class UserratingEntity {
    private int iduserrating;
    private String iduser;
    private Integer rating;
    private Integer positive;
    private Integer negative;

    @Id
    @Column(name = "IDUSERRATING")
    public int getIduserrating() {
        return iduserrating;
    }

    public void setIduserrating(int iduserrating) {
        this.iduserrating = iduserrating;
    }

    @Basic
    @Column(name = "IDUSER")
    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "RATING")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "POSITIVE")
    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    @Basic
    @Column(name = "NEGATIVE")
    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserratingEntity that = (UserratingEntity) o;

        if (iduserrating != that.iduserrating) return false;
        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (positive != null ? !positive.equals(that.positive) : that.positive != null) return false;
        if (negative != null ? !negative.equals(that.negative) : that.negative != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iduserrating;
        result = 31 * result + (iduser != null ? iduser.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (positive != null ? positive.hashCode() : 0);
        result = 31 * result + (negative != null ? negative.hashCode() : 0);
        return result;
    }
}
