package com.example.service;

import com.example.binding.EligResponse;

public interface EdService {
	
	public EligResponse determineEligibility(Long caseNum);

}
