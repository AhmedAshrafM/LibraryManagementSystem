package org.maidscc.librarymanagementsystem.dtos;

public class ApiErrorDto {

    private String message;
    private Object details;

    public ApiErrorDto(Object details, String message) {
        this.details = details;
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
