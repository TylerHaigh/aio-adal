package com.pacificnational.adal;

public class InvalidAuthenticationResult {

    private String message = "";
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public InvalidAuthenticationResult(String message) { this.message = message; }

}
