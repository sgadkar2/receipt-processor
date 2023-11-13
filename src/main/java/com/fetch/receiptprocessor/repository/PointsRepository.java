package com.fetch.receiptprocessor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fetch.receiptprocessor.entity.PointsEntity;

public interface PointsRepository extends JpaRepository<PointsEntity, String>{
    
    Optional<PointsEntity> findById(String id);
}
