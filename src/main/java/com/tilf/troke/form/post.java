package com.tilf.troke.form;

import javax.validation.constraints.NotNull;

public class post {

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @NotNull
    private String Username;

    @NotNull
    private String Password;

}
