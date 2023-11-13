package com.fetch.receiptprocessor.response;

public class GetPointsResponse {
    
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "GetPointsResponse [points=" + points + "]";
    }

}
