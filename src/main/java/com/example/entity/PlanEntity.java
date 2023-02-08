package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "PLAN_MASTER")
public class PlanEntity {
	
	private Integer planId;
	private String planName;
	

}
