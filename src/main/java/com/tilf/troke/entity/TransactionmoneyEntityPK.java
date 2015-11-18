package com.tilf.troke.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Shaun Cooper on 2015-11-18.
 */
public class TransactionmoneyEntityPK implements Serializable {
    private int idtransaction;
    private String iduser;

    @Column(name = "idtransaction")
    @Id
    public int getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(int idtransaction) {
        this.idtransaction = idtransaction;
    }

    @Column(name = "IDUSER")
    @Id
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

        TransactionmoneyEntityPK that = (TransactionmoneyEntityPK) o;

        if (idtransaction != that.idtransaction) return false;
        if (iduser != null ? !iduser.equals(that.iduser) : that.iduser != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtransaction;
        result = 31 * result + (iduser != null ? iduser.hashCode() : 0);
        return result;
    }
}
