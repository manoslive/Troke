package com.tilf.troke.auth;

import com.tilf.troke.entity.UsersEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by jp on 2015-09-21.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthUserContext {

    private UsersEntity user;

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public boolean isAuthenticated() {
        return !(user == null);
    }
}