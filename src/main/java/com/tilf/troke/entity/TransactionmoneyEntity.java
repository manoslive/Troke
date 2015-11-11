package com.tilf.troke.entity;

import javax.persistence.*;

/**
 * Created by Emmanuel on 2015-11-11.
 */
@Entity
@Table(name = "transactionmoney", schema = "", catalog = "troke")
public class TransactionmoneyEntity {
    private int idtransactionmoney;
    private int idtransaction;
    private int value;

    @Id
    @Column(name = "idtransactionmoney")
    public int getIdtransactionmoney() {
        return idtransactionmoney;
    }

    public void setIdtransactionmoney(int idtransactionmoney) {
        this.idtransactionmoney = idtransactionmoney;
    }

    @Basic
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionmoneyEntity that = (TransactionmoneyEntity) o;

        if (idtransactionmoney != that.idtransactionmoney) return false;
        if (idtransaction != that.idtransaction) return false;
        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtransactionmoney;
        result = 31 * result + idtransaction;
        result = 31 * result + value;
        return result;
    }
}
