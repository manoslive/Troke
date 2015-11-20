package com.tilf.troke.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 <<<<<<< HEAD
 * Created by Alex on 2015-11-18.
 =======
 * Created by Emmanuel on 2015-11-11.
 >>>>>>> origin/master
 */
@Entity
@Table(name = "objects", schema = "", catalog = "troke")
public class ObjectsEntity {
    private int idobject;
    private String nameObject;
    private String descObject;
    private String guid;
    private int idsubcategory;
    private Integer valueObject;
    private int quality;
    private String iduser;
    private String rateable;
    private String issignaled;
    private Date creationdate;
    private String photo1;
    private String photo2;
    private String photo3;

    @GeneratedValue
    @Id
    @Column(name = "IDOBJECT")
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Basic
    @Column(name = "NAME_OBJECT")
    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    @Basic
    @Column(name = "DESC_OBJECT")
    public String getDescObject() {
        return descObject;
    }

    public void setDescObject(String descObject) {
        this.descObject = descObject;
    }

    @Basic
    @Column(name = "GUID")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Basic
    @Column(name = "IDSUBCATEGORY")
    public int getIdsubcategory() {
        return idsubcategory;
    }

    public void setIdsubcategory(int idsubcategory) {
        this.idsubcategory = idsubcategory;
    }

    @Basic
    @Column(name = "VALUE_OBJECT")
    public Integer getValueObject() {
        return valueObject;
    }

    public void setValueObject(Integer valueObject) {
        this.valueObject = valueObject;
    }

    @Basic
    @Column(name = "QUALITY")
    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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
    @Column(name = "RATEABLE")
    public String getRateable() {
        return rateable;
    }

    public void setRateable(String rateable) {
        this.rateable = rateable;
    }

    @Basic
    @Column(name = "ISSIGNALED")
    public String getIssignaled() {
        return issignaled;
    }

    public void setIssignaled(String issignaled) {
        this.issignaled = issignaled;
    }

    @Basic
    @Column(name = "CREATIONDATE")
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "PHOTO_1")
    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    @Basic
    @Column(name = "PHOTO_2")
    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    @Basic
    @Column(name = "PHOTO_3")
    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObjectsEntity that = (ObjectsEntity) o;

        if (idobject != that.idobject) return false;
        if (idsubcategory != that.idsubcategory) return false;
        if (quality != that.quality) return false;
        if (nameObject != null ? !nameObject.equals(that.nameObject) : that.nameObject != null) return false;
        if (descObject != null ? !descObject.equals(that.descObject) : that.descObject != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (valueObject != null ? !valueObject.equals(that.valueObject) : that.valueObject != null) return false;
        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;
        if (rateable != null ? !rateable.equals(that.rateable) : that.rateable != null) return false;
        if (issignaled != null ? !issignaled.equals(that.issignaled) : that.issignaled != null) return false;
        if (creationdate != null ? !creationdate.equals(that.creationdate) : that.creationdate != null) return false;
        if (photo1 != null ? !photo1.equals(that.photo1) : that.photo1 != null) return false;
        if (photo2 != null ? !photo2.equals(that.photo2) : that.photo2 != null) return false;
        if (photo3 != null ? !photo3.equals(that.photo3) : that.photo3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idobject;
        result = 31 * result + (nameObject != null ? nameObject.hashCode() : 0);
        result = 31 * result + (descObject != null ? descObject.hashCode() : 0);
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + idsubcategory;
        result = 31 * result + (valueObject != null ? valueObject.hashCode() : 0);
        result = 31 * result + quality;
        result = 31 * result + (iduser != null ? iduser.hashCode() : 0);
        result = 31 * result + (rateable != null ? rateable.hashCode() : 0);
        result = 31 * result + (issignaled != null ? issignaled.hashCode() : 0);
        result = 31 * result + (creationdate != null ? creationdate.hashCode() : 0);
        result = 31 * result + (photo1 != null ? photo1.hashCode() : 0);
        result = 31 * result + (photo2 != null ? photo2.hashCode() : 0);
        result = 31 * result + (photo3 != null ? photo3.hashCode() : 0);
        return result;
    }
}
