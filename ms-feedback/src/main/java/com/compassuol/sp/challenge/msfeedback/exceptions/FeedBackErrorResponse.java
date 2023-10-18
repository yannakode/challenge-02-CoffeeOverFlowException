package com.compassuol.sp.challenge.msfeedback.exceptions;

public class FeedBackErrorResponse {
    private Integer code;
    private String status;
    private String message;
    private DetailsFeedBackErrorResponse details;
    }
    class DetailsFeedBackErrorResponse{
        private String fields;
        private String message;
        public DetailsFeedBackErrorResponse(String fields, String message) {
            this.fields = fields;
            this.message = message;

    }
}