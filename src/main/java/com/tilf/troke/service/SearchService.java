package com.tilf.troke.service;

import com.tilf.troke.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Manu on 2015-10-19.
 */
@Service
public class SearchService {
    @Autowired
    private CustomUserRepository customUser;

    // Logique métier
}
