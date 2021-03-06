package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Alex on 2015-11-19.
 */
@Entity
@Table(name = "imageobject", schema = "", catalog = "troke")
@IdClass(ImageobjectEntityPK.class)
public class ImageobjectEntity {
    private int idobject;
    private String guidimage;
    private String ismain;

    @Id
    @Column(name = "IDOBJECT")
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Id
    @Column(name = "GUIDIMAGE")
    public String getGuidimage() {
        return guidimage;
    }

    public void setGuidimage(String guidimage) {
        this.guidimage = guidimage;
    }

    @Basic
    @Column(name = "ISMAIN")
    public String getIsmain() {
        return ismain;
    }

    public void setIsmain(String ismain) {
        this.ismain = ismain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageobjectEntity that = (ImageobjectEntity) o;

        if (idobject != that.idobject) return false;
        if (guidimage != null ? !guidimage.equals(that.guidimage) : that.guidimage != null) return false;
        if (ismain != null ? !ismain.equals(that.ismain) : that.ismain != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idobject;
        result = 31 * result + (guidimage != null ? guidimage.hashCode() : 0);
        result = 31 * result + (ismain != null ? ismain.hashCode() : 0);
        return result;
    }
}
