package com.tilf.troke.filter;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Manu on 2015-10-19.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchFilter {
    private Map<String, Boolean> filters;

    public SearchFilter() {
        filters = new ConcurrentHashMap<>();
    }

    public void put(String filter, Boolean value) {
        filters.put(filter, value);
    }

    public Boolean get(String filter) {
        return filters.get(filter);
    }

    public Map<String, Boolean> getFilters() {
        return filters;
    }

    public boolean remove(String filter){
        for(Iterator<Map.Entry<String, Boolean>> it = filters.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Boolean> entry = it.next();
            if (entry.getKey().equals(filter)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public void removeAll() {
        if (!filters.isEmpty()) {
            filters = new HashMap<>();
        }
    }
}
