package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "chat", schema = "", catalog = "troke")
public class ChatEntity {
    private int idchat;
    private int idtransaction;

    @Id
    @Column(name = "IDCHAT")
    public int getIdchat() {
        return idchat;
    }

    public void setIdchat(int idchat) {
        this.idchat = idchat;
    }

    @Basic
    @Column(name = "IDTRANSACTION")
    public int getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(int idtransaction) {
        this.idtransaction = idtransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatEntity that = (ChatEntity) o;

        if (idchat != that.idchat) return false;
        if (idtransaction != that.idtransaction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idchat;
        result = 31 * result + idtransaction;
        return result;
    }
}
