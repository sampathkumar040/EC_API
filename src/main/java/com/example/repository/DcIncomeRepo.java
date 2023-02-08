package com.example.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.DC_Income;

public interface DcIncomeRepo extends JpaRepository<DC_Income, Serializable> {
	
	public DC_Income findByCaseNum(Long caseNum);

}
