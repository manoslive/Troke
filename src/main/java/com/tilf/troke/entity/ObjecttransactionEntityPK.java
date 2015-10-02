package com.tilf.troke.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Manu on 2015-10-02.
 */
public class ObjecttransactionEntityPK implements Serializable {
    private int idobject;
    private int idtransaction;

    @Column(name = "IDOBJECT")
    @Id
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Column(name = "IDTRANSACTION")
    @Id
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

        ObjecttransactionEntityPK that = (ObjecttransactionEntityPK) o;

        if (idobject != that.idobject) return false;
        if (idtransaction != that.idtransaction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idobject;
        result = 31 * result + idtransaction;
        return result;
    }
}
