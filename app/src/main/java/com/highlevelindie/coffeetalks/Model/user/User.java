package com.highlevelindie.coffeetalks.Model.user;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String userID;
    private String nickname;
    private String email;
    private String password;
    private ArrayList<String> chatgroups;
    private Map<String,Object> userMap;


    public User(String email,String password) {
        this.password = password;
        this.email = email;
        this.userMap = new HashMap<>();
        userMap.put("email",email);
    }

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.userMap = new HashMap<>();
        userMap.put("nick",nickname);
        userMap.put("email",email);
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getChatgroups() {
        return chatgroups;
    }

    public void setChatgroups(ArrayList<String> chatgroups) {
        this.chatgroups = chatgroups;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, Object> userMap) {
        this.userMap = userMap;
    }

    public boolean SignUp(Activity v){
        DaoUser dao = new DaoUser();
        return dao.SignUp(nickname,email,password,v);
    }
    public boolean SignIn(Activity v){
        DaoUser dao = new DaoUser();
        return dao.SignIn(email,password,v);
    }
}
