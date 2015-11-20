package com.tilf.troke.service;

import com.tilf.troke.repository.CustomObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Manu on 2015-11-18.
 */

@Service
public class CatSubCatService {
    @Autowired
    private CustomObjectRepository customObjectRepository;

    // Sert à obtenir toutes les catégories de la liste mélangée
    // de catégories/sous-catégories
    public Set<String> getCatFromSet(Set<String> catSubCatSet) {
        List<String> catList = customObjectRepository.getAllCategories();
        Set<String> catSet = new HashSet<>();
        for (String item : catList) {
            for (String item2 : catSubCatSet) {
                if(item2.equals(item)){
                    catSet.add(item2);
                }
            }
        }
        return catSet;
    }
    // Sert à obtenir toutes les sous-catgégories de chaque catégories
    // de la liste mélangée de catégories/sous-catégories
    public Set<String> getSubCatFromSet(Set<String> catSubCatSet) {
        List<String> catList = customObjectRepository.getAllCategories();
        Set<String> catSet = null;
        for(String category : catList) {
            List<String> subCatList = customObjectRepository.getAllSubCategories(category);
            for (String item : subCatList) {
                for (String item2 : catSubCatSet) {
                    if (item2.equals(item)) {
                        catSet.add(item2);
                    }
                }
            }
        }
        return catSet;
    }
}
