package com.tilf.troke.repository;

import com.tilf.troke.entity.ImageobjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Alex on 2015-11-19.
 */
@Repository
public interface ImageObjectRepository extends JpaRepository<ImageobjectEntity, String> {

    ImageobjectEntity save(ImageobjectEntity image);
}
