package com.example.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DC_Kids;

public interface DcKidRepo extends JpaRepository<DC_Kids, Serializable> {

	public List<DC_Kids> findByCaseNum(Long caseNum);
	
}
