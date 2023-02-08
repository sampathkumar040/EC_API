package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="ELIG_DTLS")
public class EdEligDetailsEntity {
	
	@Id
	@GeneratedValue
	private Integer edTraceId;
	
	private Long caseNum;
	
	private String holderName;
	
	private Long holderSsn;
	
	private String planName;
	
	private String planStatus;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private Double benefitAmt;
	
	private String denailReason;
	
	
	
	

}
