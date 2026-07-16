package com.myy803.project.domain;

public class ChatRequest {
    private String message;
    private String currentPage;

    public ChatRequest() {
    }

    public ChatRequest(String message, String currentPage) {
        this.message = message;
        this.currentPage = currentPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
