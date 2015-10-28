package com.tilf.troke.listener;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Manu on 2015-10-27.
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
