package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_EDUCATION")

public class DC_Education {
	
	@Id
	@GeneratedValue
	private Integer educationId;
	
	private String highestDegree;
	private String universityName;	
	private Integer graduationYear;	
	private Long caseNum;

}
