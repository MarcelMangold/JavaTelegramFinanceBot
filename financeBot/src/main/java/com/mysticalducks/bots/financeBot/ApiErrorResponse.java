package com.mysticalducks.bots.financeBot;

public class ApiErrorResponse {

	private String status;
	private ApiError error;

	public ApiErrorResponse() {
	}

	public ApiErrorResponse(String status, ApiError error) {
		this.status = status;
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ApiError getError() {
		return error;
	}

}
