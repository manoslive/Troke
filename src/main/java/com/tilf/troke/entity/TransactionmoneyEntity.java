package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Shaun Cooper on 2015-11-18.
 */
@Entity
@Table(name = "transactionmoney", schema = "", catalog = "troke")
@IdClass(TransactionmoneyEntityPK.class)
public class TransactionmoneyEntity {
    private int idtransaction;
    private int value;
    private String iduser;

    @Id
    @Column(name = "idtransaction")
    public int getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(int idtransaction) {
        this.idtransaction = idtransaction;
    }

    @Basic
    @Column(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Id
    @Column(name = "IDUSER")
    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionmoneyEntity that = (TransactionmoneyEntity) o;

        if (idtransaction != that.idtransaction) return false;
        if (value != that.value) return false;
        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtransaction;
        result = 31 * result + value;
        result = 31 * result + (iduser != null ? iduser.hashCode() : 0);
        return result;
    }
}
