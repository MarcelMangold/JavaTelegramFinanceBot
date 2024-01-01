package com.mysticalducks.bots.financeBot;

public class User {

    private String name;
    private long telegramUserId;
    private String password;
    private String email;
    private int language;

    public User(String name, long telegramUserId) {
    	this.name = name;
    	this.telegramUserId = telegramUserId;
    }
    
    public User() {  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(int telegramUserId) {
        this.telegramUserId = telegramUserId;
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

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

}

