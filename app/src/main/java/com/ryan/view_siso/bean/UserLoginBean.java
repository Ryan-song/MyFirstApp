package com.ryan.view_siso.bean;

/**
 * Created by air on 15/10/29.
 */
public class UserLoginBean {

    /**
     * username : 20090413023
     * password : 123
     * type : 老师
     */

    private String username;
    private String password;
    private String type;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

}