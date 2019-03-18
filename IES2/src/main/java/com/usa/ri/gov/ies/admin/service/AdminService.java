package com.usa.ri.gov.ies.admin.service;

import java.util.List;

import com.usa.ri.gov.ies.admin.model.AppAccount;
import com.usa.ri.gov.ies.admin.model.AppPlan;

public interface AdminService {

	public boolean registerAccount(AppAccount appAccount);

	public String findByEmail(String emailId);
	
	public List<AppAccount> findAllAppAccounts();
	
	public boolean updateAccountSw(String accId,String activeSw);
	public boolean registerPlan(AppPlan appplan);
	//public String findByName(String planName);
	public List<AppPlan> findAllAppPlans();
	public boolean updatePlanSw(String planId,String Activesw);
}
