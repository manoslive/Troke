package com.tilf.troke.repository;

import com.tilf.troke.entity.ObjectsEntity;
import com.tilf.troke.entity.UsersEntity;

import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-21.
 */
public interface CustomTradeRepository {
    void addNewTrade(String User1, String User2, List<Integer> objects, String chatLog);
}
