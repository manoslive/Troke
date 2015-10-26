package com.tilf.troke.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Emmanuel on 2015-10-21.
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

    @Id
    @GeneratedValue
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
        return result;
    }
}
