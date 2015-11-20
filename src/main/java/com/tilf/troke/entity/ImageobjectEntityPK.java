package com.tilf.troke.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Alex on 2015-11-19.
 */
public class ImageobjectEntityPK implements Serializable {
    private int idobject;
    private String guidimage;

    @Column(name = "IDOBJECT")
    @Id
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Column(name = "GUIDIMAGE")
    @Id
    public String getGuidimage() {
        return guidimage;
    }

    public void setGuidimage(String guidimage) {
        this.guidimage = guidimage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageobjectEntityPK that = (ImageobjectEntityPK) o;

        if (idobject != that.idobject) return false;
        if (guidimage != null ? !guidimage.equals(that.guidimage) : that.guidimage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idobject;
        result = 31 * result + (guidimage != null ? guidimage.hashCode() : 0);
        return result;
    }
}
