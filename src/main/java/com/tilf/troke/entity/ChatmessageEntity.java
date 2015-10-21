package com.tilf.troke.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Emmanuel on 2015-10-21.
 */
@Entity
@Table(name = "chatmessage", schema = "", catalog = "troke")
public class ChatmessageEntity {
    private int idchatmessage;
    private String msg;
    private Date dateTime;
    private int idchat;
    private String iduser;
    private String isread;

    @Id
    @Column(name = "IDCHATMESSAGE")
    public int getIdchatmessage() {
        return idchatmessage;
    }

    public void setIdchatmessage(int idchatmessage) {
        this.idchatmessage = idchatmessage;
    }

    @Basic
    @Column(name = "MSG")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Basic
    @Column(name = "DATE_TIME")
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "IDCHAT")
    public int getIdchat() {
        return idchat;
    }

    public void setIdchat(int idchat) {
        this.idchat = idchat;
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
    @Column(name = "ISREAD")
    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatmessageEntity that = (ChatmessageEntity) o;

        if (idchatmessage != that.idchatmessage) return false;
        if (idchat != that.idchat) return false;
        if (msg != null ? !msg.equals(that.msg) : that.msg != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;
        if (isread != null ? !isread.equals(that.isread) : that.isread != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idchatmessage;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + idchat;
        result = 31 * result + (iduser != null ? iduser.hashCode() : 0);
        result = 31 * result + (isread != null ? isread.hashCode() : 0);
        return result;
    }
}
