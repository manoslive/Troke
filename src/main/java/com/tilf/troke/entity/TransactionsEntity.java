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
    private String iduser1;
    private String iduser2;
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
    @Column(name = "IDUSER1")
    public String getIduser1() {
        return iduser1;
    }

    public void setIduser1(String iduser1) {
        this.iduser1 = iduser1;
    }

    @Basic
    @Column(name = "IDUSER2")
    public String getIduser2() {
        return iduser2;
    }

    public void setIduser2(String iduser2) {
        this.iduser2 = iduser2;
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
        if (iduser1 != null ? !iduser1.equals(that.iduser1) : that.iduser1 != null) return false;
        if (iduser2 != null ? !iduser2.equals(that.iduser2) : that.iduser2 != null) return false;
        if (datetransaction != null ? !datetransaction.equals(that.datetransaction) : that.datetransaction != null)
            return false;
        if (iscompleted != null ? !iscompleted.equals(that.iscompleted) : that.iscompleted != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idtransaction;
        result = 31 * result + (iduser1 != null ? iduser1.hashCode() : 0);
        result = 31 * result + (iduser2 != null ? iduser2.hashCode() : 0);
        result = 31 * result + (datetransaction != null ? datetransaction.hashCode() : 0);
        result = 31 * result + (iscompleted != null ? iscompleted.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
