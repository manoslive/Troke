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
}
