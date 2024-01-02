package com.mysticalducks.bots.financeBot;

public enum ApiErrorCodes {
    DATA_NOT_FOUND(1, "Data not found"),
    USER_NOT_FOUND(2, "User not found"),
    CHAT_NOT_FOUND(3, "Chat not found"),
    ICON_NOT_FOUND(4, "Icon not found"),
    CATEGORY_NOT_FOUND(5, "Category not found"),
    TRANSACTION_NOT_FOUND(6, "Transaction not found");

    private final int code;
    private final String message;

    ApiErrorCodes(int code, String message) {
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
