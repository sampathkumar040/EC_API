package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.EligResponse;
import com.example.service.EdService;

@RestController
public class EdRestController {
	@Autowired
	private EdService edService;
	
	@GetMapping("/eligibility/{caseNum}")
	public ResponseEntity<EligResponse> determine(@PathVariable Long caseNum){
		EligResponse status = edService.determineEligibility(caseNum);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
	

}
