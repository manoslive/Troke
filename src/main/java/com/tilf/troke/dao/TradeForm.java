package com.tilf.troke.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-21.
 */
public class TradeForm {
    private String iduser1;
    private String iduser2;
    private List<Integer> objects;
    private String chatMsg;

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getIduser1() {
        return iduser1;
    }

    public void setIduser1(String iduser1) {
        this.iduser1 = iduser1;
    }

    public String getIduser2() {
        return iduser2;
    }

    public void setIduser2(String iduser2) {
        this.iduser2 = iduser2;
    }

    public List<Integer> getObjects() {
        if(objects == null) {
            objects = new ArrayList<Integer>();
        }
        return objects;
    }

    public void setObjects(List<Integer> objects) {
        this.objects = objects;
    }
}
