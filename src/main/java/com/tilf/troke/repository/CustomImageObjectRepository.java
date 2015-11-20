package com.tilf.troke.repository;

import com.tilf.troke.entity.ImageobjectEntity;

import java.util.List;

/**
 * Created by Alex on 2015-11-19.
 */
public interface CustomImageObjectRepository {
    List<ImageobjectEntity> getImageObjectbyObjectId(int objectID);
}
