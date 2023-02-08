package com.example.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DC_Education;

public interface DcEducationRepo extends JpaRepository<DC_Education, Serializable> {
	
	public DC_Education findByCaseNum(Long caseNum);
}
