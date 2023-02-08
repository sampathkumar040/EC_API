package com.example.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EdEligDetailsEntity;

public interface EdEligRepository extends JpaRepository<EdEligDetailsEntity, Serializable>{

}
