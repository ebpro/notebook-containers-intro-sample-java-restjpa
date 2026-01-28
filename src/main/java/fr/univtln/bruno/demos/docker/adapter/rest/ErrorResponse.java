package fr.univtln.bruno.demos.docker.adapter.rest;

/**
 * Represents an error response with a code, message, and details.
 * This class is used to structure error information returned by the API.
 */
public class ErrorResponse {

    private String code;
    private String message;
    private String details;

    public ErrorResponse() {
        // required for JSON serialization/deserialization
    }

    public ErrorResponse(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
