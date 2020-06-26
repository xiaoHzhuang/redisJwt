package com.inspur.system.security.po;

public class SystemUser {
    private String userid;

    private String username;

    private String usercaption;

    private String phone;

    private String pwd;

    private String email;
    private String checkPassword;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {

        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUsercaption() {
        return usercaption;
    }

    public void setUsercaption(String usercaption) {
        this.usercaption = usercaption == null ? null : usercaption.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {

        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email == null ? null : email.trim();
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}