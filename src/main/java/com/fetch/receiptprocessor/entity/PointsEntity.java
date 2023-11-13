package com.fetch.receiptprocessor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Points")
public class PointsEntity {
    
    @Id
    @Column(name = "id")
	private String id;

	@Column(name = "points")
	private int points;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "PointsEntity [id=" + id + ", points=" + points + "]";
    }
}
