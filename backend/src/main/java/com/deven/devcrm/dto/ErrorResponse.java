//  This class will control how our error response looks.

package com.deven.devcrm.dto;

public class ErrorResponse {

    private int status;
    private String message;
    private Long timestamp;

    public ErrorResponse(int status, String message, Long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
