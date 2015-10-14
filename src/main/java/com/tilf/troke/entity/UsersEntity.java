package com.tilf.troke.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by Manu on 2015-10-02.
 *
 *
 *
 */
@Entity
@Table(name = "users", schema = "", catalog = "troke")
public class UsersEntity {
    @NotNull
    @Id
    @Column(name = "IDUSER")
    private String iduser;
    @NotNull
    @Basic
    @Column(name = "LASTNAME")
    private String lastname;
    @NotNull
    @Basic
    @Column(name = "FIRSTNAME")
    private String firstname;
    @NotNull
    @Basic
    @Column(name = "PASS")
    private String pass;
    @Basic
    @Column(name = "AVATAR")
    private String avatar;
    @NotNull
    @Basic
    @Column(name = "ISBANNED")
    private String isbanned;
    @NotNull
    @Basic
    @Column(name = "ISONLINE")
    private String isonline;
    @NotNull
    @Basic
    @Column(name = "CREATIONDATE")
    private Date creationdate;
    @NotNull
    @Basic
    @Column(name = "EMAIL")
    private String email;
    @NotNull
    @Basic
    @Column(name = "TELEPHONE")
    private String telephone;
    @NotNull
    @Basic
    @Column(name = "ZIPCODE")
    private String zipcode;
    @NotNull
    @Basic
    @Column(name = "PERMISSIONLEVEL")
    private Integer permissionlevel;
    @NotNull
    @Basic
    @Column(name = "ISVIP")
    private String isvip;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsbanned() {
        return isbanned;
    }

    public void setIsbanned(String isbanned) {
        this.isbanned = isbanned;
    }

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getPermissionlevel() {
        return permissionlevel;
    }

    public void setPermissionlevel(Integer permissionlevel) {
        this.permissionlevel = permissionlevel;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        if (isbanned != null ? !isbanned.equals(that.isbanned) : that.isbanned != null) return false;
        if (isonline != null ? !isonline.equals(that.isonline) : that.isonline != null) return false;
        if (creationdate != null ? !creationdate.equals(that.creationdate) : that.creationdate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (zipcode != null ? !zipcode.equals(that.zipcode) : that.zipcode != null) return false;
        if (permissionlevel != null ? !permissionlevel.equals(that.permissionlevel) : that.permissionlevel != null)
            return false;
        if (isvip != null ? !isvip.equals(that.isvip) : that.isvip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iduser != null ? iduser.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (isbanned != null ? isbanned.hashCode() : 0);
        result = 31 * result + (isonline != null ? isonline.hashCode() : 0);
        result = 31 * result + (creationdate != null ? creationdate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (permissionlevel != null ? permissionlevel.hashCode() : 0);
        result = 31 * result + (isvip != null ? isvip.hashCode() : 0);
        return result;
    }
}
