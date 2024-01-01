package com.mysticalducks.bots.financeBot;

import org.apache.http.HttpStatus;

public class ApiErrorResponse {
	
	private HttpStatus status;
    private ApiError error;

	    public ApiErrorResponse(HttpStatus status, ApiError error) {
	        this.status = status;
	        this.error = error;
	    }

		public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public ApiError getError() {
			return error;
		}
	    

}
