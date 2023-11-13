package com.fetch.receiptprocessor.request;

public class Item {
    
    private String shortDescription;
    private String price;

    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Item [shortDescription=" + shortDescription + ", price=" + price + "]";
    }    
}
