package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_KIDS")

public class DC_Kids {
	@Id
	@GeneratedValue
	private Integer kidId;
	
	private String kidName;
	
	private LocalDate kidDob;
	
	private Long kidSsn;	
	
	private String kidsGender;
	
	private Long caseNum;
	
	
}
