package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="DC_INCOME")
public class DC_Income {
	@Id
	@GeneratedValue	
	private Integer incomeId;	
	
	private Double salaryIncome;
	
	private Integer rentIncome;
	
	private Integer propertyIncome;
	
	private Long caseNum;

}
