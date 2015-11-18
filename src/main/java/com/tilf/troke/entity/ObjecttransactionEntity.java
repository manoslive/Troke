package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "objecttransaction", schema = "", catalog = "troke")
@IdClass(ObjecttransactionEntityPK.class)
public class ObjecttransactionEntity {
    private int idobject;
    private int idtransaction;

    @Id
    @Column(name = "IDOBJECT")
    public int getIdobject() {
        return idobject;
    }

    public void setIdobject(int idobject) {
        this.idobject = idobject;
    }

    @Id
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

        ObjecttransactionEntity that = (ObjecttransactionEntity) o;

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
