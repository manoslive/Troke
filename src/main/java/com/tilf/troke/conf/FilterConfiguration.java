package com.tilf.troke.conf;

import com.tilf.troke.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Created by jp on 2015-09-21.
 */
@Configuration
public class FilterConfiguration {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean remoteAddressFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(authFilter);
        filterRegistrationBean.addUrlPatterns("/site/*");
        return filterRegistrationBean;
    }
}
