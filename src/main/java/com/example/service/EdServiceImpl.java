package com.example.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.binding.EligResponse;
import com.example.entity.CitizenAppEntity;
import com.example.entity.CoTrgEntity;
import com.example.entity.DC_Education;
import com.example.entity.DC_Income;
import com.example.entity.DC_Kids;
import com.example.entity.DcCaseEntity;
import com.example.entity.EdEligDetailsEntity;
import com.example.entity.PlanEntity;
import com.example.repository.CitizenAppRepository;
import com.example.repository.CoTriggerRepository;
import com.example.repository.DcCaseRepo;
import com.example.repository.DcEducationRepo;
import com.example.repository.DcIncomeRepo;
import com.example.repository.DcKidRepo;
import com.example.repository.EdEligRepository;
import com.example.repository.PlanRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class EdServiceImpl implements EdService	 {
	@Autowired
	private DcCaseRepo caseRepo;
	@Autowired
	private PlanRepository planRepo;
	@Autowired
	private DcIncomeRepo incomeRepo;
	@Autowired
	private DcKidRepo kidRepo;
	@Autowired
	private CitizenAppRepository citizenRepo;
	@Autowired
	private DcEducationRepo educationRepo;
	@Autowired
	private CoTriggerRepository coTriggerRepo;
	@Autowired
	private EdEligRepository edEligRepo;
	
	boolean noKids=false;
	boolean ageFlag=true;
	 

	@Override
	public EligResponse determineEligibility(Long caseNum) {
		Integer planId=null;
		String  planName=null;
		Integer appId=null;
		
		EligResponse response =new EligResponse();
		
		Optional<DcCaseEntity> dcCaseEntity = caseRepo.findById(caseNum);
		if(dcCaseEntity.isPresent()) {
			planId = dcCaseEntity.get().getPlanId();
			appId=dcCaseEntity.get().getAppId();
		}
		
		Optional<PlanEntity> planEntity = planRepo.findById(planId);
		if(planEntity.isPresent()) {
			planName=planEntity.get().getPlanName();
		}
		
		DC_Income income = incomeRepo.findByCaseNum(caseNum);
		
		List<DC_Kids> kids = kidRepo.findByCaseNum(caseNum);
		
		Optional<CitizenAppEntity> citizenAppEntity = citizenRepo.findById(appId);
		CitizenAppEntity citizenApp = citizenAppEntity.get();
		
		
		if("SNAP".equals(planName)) {
			if(income.getSalaryIncome()>300) {
				response.setPlanStatus("Denail");
				response.setDenailReason("High Salary Income");
			}
			
		}else if("CCAP".equals(planName)) {
			
			
			if(!kids.isEmpty()) {
				kids.forEach(kid->{
					LocalDate dob= kid.getKidDob();
					LocalDate today=LocalDate.now();
					Period p = Period.between(dob, today);
					int year =p.getYears();
					if(year >16) {
						ageFlag=true;
					}
					
				});
			}else {
				response.setDenailReason("No Kids Available");
				noKids=false;
			}
			
			if(income.getSalaryIncome()>300) {
				
				response.setPlanStatus("Denail");
				response.setDenailReason("High Salary Income");
			}
			if(noKids && income.getSalaryIncome()>300) {
				
				response.setPlanStatus("Denail");
				response.setDenailReason("High Salary Income + No kids");
			}
			if(!ageFlag) {
				response.setPlanStatus("Denail");
				response.setDenailReason("kid age is > 16");
			}
			if(income.getSalaryIncome()>300 && !ageFlag) {
				response.setPlanStatus("Denail");
				response.setDenailReason("High Salary Income  && kid age is > 16");
			}
			
		}else if("Medicaid".equals(planName)) {
			
			Double salaryIncome = income.getSalaryIncome();
			Integer rentIncome = income.getRentIncome();
			Integer propertyIncome = income.getPropertyIncome();
			
			if(salaryIncome >300) {
				response.setDenailReason("High Salary Reason");
				
			}
			if(rentIncome>0) {
				response.setDenailReason("Rental Income Available");
			}
			if(propertyIncome>0) {
				response.setDenailReason("Property income is Available");
				
			}
			if(rentIncome>0 && propertyIncome>0  ) {
				response.setDenailReason("Rental + Property income is Available");
				
			}
			if(salaryIncome>300 && rentIncome>0 && propertyIncome>0  ) {
				response.setDenailReason("Salary is High + Rental + Property income is Available");
				
			}
			
			
		}else if("Medicare".equals(planName)) {
			LocalDate dob = citizenApp.getDob();
			LocalDate today=LocalDate.now();
			Period between = Period .between(dob, today);
			int year=between.getYears();
			if(year<65) {
				response.setDenailReason("Age is < 65 years");
			}
			
		}else if("RIW".equals(planName)) {
			DC_Education educationEntity = educationRepo.findByCaseNum(caseNum);
			Integer graduationYear = educationEntity.getGraduationYear();
			if(graduationYear<=0) {
				response.setDenailReason("Not graduated");
			}
			if(income.getSalaryIncome()>0) {
				response.setDenailReason("Already Graduated");
			}
			
		}
		response.setPlanName(planName);
		if(response.getDenailReason()!=null) {
			response.setPlanStatus("Denied");
			
		}else {
			response.setPlanStatus("Approved");
			response.setPlanStartDate(LocalDate.now().plusDays(1));
			response.setPlanStartDate(LocalDate.now().plusMonths(1));
			response.setBenefitAmt(300.00);
		}
		
		EdEligDetailsEntity edEntity=new EdEligDetailsEntity();
		BeanUtils.copyProperties(response, edEntity);
		edEligRepo.save(edEntity);
		
		
		CoTrgEntity coTrgEntity=new CoTrgEntity();
		coTrgEntity.setCaseNum(caseNum);
		coTrgEntity.setTrgStatus("Pending");
		coTriggerRepo.save(coTrgEntity);
		
		
		return response;
		
		
	}

}
