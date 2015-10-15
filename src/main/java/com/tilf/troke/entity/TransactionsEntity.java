package com.tilf.troke.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Manu on 2015-10-15.
 */
@Entity
@Table(name = "transactions", schema = "", catalog = "troke")
public class TransactionsEntity {
    private int idtransaction;
    private Date datetransaction;
    private String iscompleted;
    private String state;

    @Id
    @Column(name = "IDTRANSACTION")
    public int getIdtransaction() {
        return idtransaction;
    }

    public void setIdtransaction(int idtransaction) {
        this.idtransaction = idtransaction;
    }

    @Basic
    @Column(name = "DATETRANSACTION")
    public Date getDatetransaction() {
        return datetransaction;
    }

    public void setDatetransaction(Date datetransaction) {
        this.datetransaction = datetransaction;
    }

    @Basic
    @Column(name = "ISCOMPLETED")
    public String getIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(String iscompleted) {
        this.iscompleted = iscompleted;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionsEntity that = (TransactionsEntity) o;

        if (idtransaction != that.idtransaction) return false;
        if (datetransaction != null ? !datetransaction.equals(that.datetransaction) : that.datetransaction != null)
            return false;
        if (iscompleted != null ? !iscompleted.equals(that.iscompleted) : that.iscompleted != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtransaction;
        result = 31 * result + (datetransaction != null ? datetransaction.hashCode() : 0);
        result = 31 * result + (iscompleted != null ? iscompleted.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
