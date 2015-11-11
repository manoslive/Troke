package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "imageobject", schema = "", catalog = "troke")
public class ImageobjectEntity {
    private int idimagesobject;
    private int idobject;
    private String imagepath;
    private String ismainimage;

    @Id
    @Column(name = "IDIMAGESOBJECT")
    public int getIdimagesobject() {
        return idimagesobject;
    }

    public void setIdimagesobject(int idimagesobject) {
        this.idimagesobject = idimagesobject;
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
    @Column(name = "IMAGEPATH")
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Basic
    @Column(name = "ISMAINIMAGE")
    public String getIsmainimage() {
        return ismainimage;
    }

    public void setIsmainimage(String ismainimage) {
        this.ismainimage = ismainimage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageobjectEntity that = (ImageobjectEntity) o;

        if (idimagesobject != that.idimagesobject) return false;
        if (idobject != that.idobject) return false;
        if (imagepath != null ? !imagepath.equals(that.imagepath) : that.imagepath != null) return false;
        if (ismainimage != null ? !ismainimage.equals(that.ismainimage) : that.ismainimage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idimagesobject;
        result = 31 * result + idobject;
        result = 31 * result + (imagepath != null ? imagepath.hashCode() : 0);
        result = 31 * result + (ismainimage != null ? ismainimage.hashCode() : 0);
        return result;
    }
}
