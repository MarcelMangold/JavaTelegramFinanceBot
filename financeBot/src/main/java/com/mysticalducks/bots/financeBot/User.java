package com.mysticalducks.bots.financeBot;

public class User {

    private String name;
    private long userId;
    private String password;
    private String email;
    private int language;

    public User(String name, long userId) {
    	this.name = name;
    	this.userId = userId;
    	this.password = "default";
    	this.email = "email";
    	this.language = 0;
    }
    
    public User() {  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

