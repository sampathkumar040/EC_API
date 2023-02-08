package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="CITIZEN_APPS")
public class CitizenAppEntity {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String email;
	
	private long mobileNo;
	
	private String gender;
	
	private LocalDate dob;	
	
	private long ssn;

}
