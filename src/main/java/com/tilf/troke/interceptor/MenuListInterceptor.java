package com.tilf.troke.interceptor;

import com.tilf.troke.controller.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Manu on 2015-10-20.
 */
@Component
public class MenuListInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private HomeController homeController;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

}
