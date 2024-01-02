package com.mysticalducks.bots.financeBot;

public class ApiError {

    private int code;
    private String message;
    
    ApiError() {}

    ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}
	
