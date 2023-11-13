package com.fetch.receiptprocessor.response;

public class ReceiptProcessResponse {
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReceiptProcessResponse [id=" + id + "]";
    }

    
}
