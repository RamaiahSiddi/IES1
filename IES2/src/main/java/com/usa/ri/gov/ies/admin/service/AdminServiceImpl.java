package com.usa.ri.gov.ies.admin.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.mail.util.BEncoderStream;
import com.usa.ri.gov.ies.admin.entity.AppAccountEntity;
import com.usa.ri.gov.ies.admin.entity.PlanDetalisEntity;
import com.usa.ri.gov.ies.admin.model.AppAccount;
import com.usa.ri.gov.ies.admin.model.AppPlan;
import com.usa.ri.gov.ies.admin.repository.AppAccountRepository;
import com.usa.ri.gov.ies.admin.repository.AppPlanRepository;
import com.usa.ri.gov.ies.constants.AppConstants;
import com.usa.ri.gov.ies.properties.AppProperties;
import com.usa.ri.gov.ies.util.EmailUtils;
import com.usa.ri.gov.ies.util.PasswordUtils;

/**
 * This class is used to handler all business operations related to admin module
 * 
 * @author admin
 *
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

	private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired(required = true)
	private AppAccountRepository appAccRepository;
	@Autowired(required = true)
	private AppPlanRepository appPlanRepository;

	@Autowired(required = true)
	private EmailUtils emailUtils;

	@Autowired(required = true)
	private AppProperties appProperties;

	/**
	 * This method is used to Register cw/admin
	 */
	@Override
	public boolean registerAccount(AppAccount appAcc) {
		logger.debug("User Registration started");

		// Convert model data to Entity data
		AppAccountEntity entity = new AppAccountEntity();
		BeanUtils.copyProperties(appAcc, entity);

		// Encrypt Password
		String encryptedPwd = PasswordUtils.encrypt(appAcc.getPassword());

		// Set Encrypted password to Entity obj
		entity.setPassword(encryptedPwd);

		// set Status as Active
		entity.setActiveSw(AppConstants.ACTIVE_SW);

		// Call Repository method
		entity = appAccRepository.save(entity);

		// sending Registration Email
		try {
			String fileName = appProperties.getProperties().get(AppConstants.REG_EMAIL_FILE_NAME);
			String mailSub = appProperties.getProperties().get(AppConstants.REG_EMAIL_SUBJECT);
			String mailBody = getEmailBodyContent(appAcc, fileName);
			emailUtils.sendEmail(entity.getEmail(), mailSub, mailBody);
		} catch (Exception e) {
			logger.error("User Registration failed : " + e.getMessage());
		}
		logger.debug("User Registration completed");
		logger.info("AdminServiceImpl::registerAccount() completed");
		return (entity.getAccId() > 0) ? true : false;
	}

	/**
	 * This method is used to reg email body from a file
	 * 
	 * @param accModel
	 * @return String
	 * @throws Exception
	 */
	public String getEmailBodyContent(AppAccount accModel, String fileName) throws Exception {
		logger.debug("Reading Reg Email content started");
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuffer body = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			if (line != null && !"".equals(line) && !"<br/>".equals(line)) {
				// process
				if (line.contains("USER_NAME")) {
					line = line.replace("USER_NAME", accModel.getFirstName() + " " + accModel.getLastName());
				}
				if (line.contains("APP_URL")) {
					line = line.replace("APP_URL", "<a href='http://localhost:7070/IES/'>IES URL</a>");
				}
				if (line.contains("APP_USER_EMAIL")) {
					line = line.replace("APP_USER_EMAIL", accModel.getEmail());
				}
				if (line.contains("APP_USER_PWD")) {
					line = line.replace("APP_USER_PWD", accModel.getPassword());
				}
				// Adding processed line to SB body
				body.append(line);
			}
			// read next line
			line = br.readLine();
		}
		// closing br
		br.close();
		logger.debug("Reading Reg Email content Ended");
		logger.info("Reg Email body parsing completed");
		return body.toString();
	}

	/**
	 * This method is used to check unique email
	 */
	@Override
	public String findByEmail(String emailId) {
		AppAccountEntity entity = appAccRepository.findByEmail(emailId);
		return (entity == null) ? "Unique" : "Duplicate";
	}

	/**
	 * This method is used to return All accounts details
	 */
	@Override
	public List<AppAccount> findAllAppAccounts() {
		logger.debug("findAllAppAccounts() method started");
		List<AppAccount> models = new ArrayList<AppAccount>();
		try {
			// call Repository method
			List<AppAccountEntity> entities = appAccRepository.findAll();

			if (entities.isEmpty()) {
				logger.warn("***No Accounts found in Application****");
			} else {
				// convert Entities to models
				for (AppAccountEntity entity : entities) {
					AppAccount model = new AppAccount();
					BeanUtils.copyProperties(entity, model);
					models.add(model);
				}
				logger.info("All Accounts details loaded successfully");
			}
		} catch (Exception e) {
			logger.error("Exception occured in findAllAppAccounts()::" + e.getMessage());
		}
		logger.debug("findAllAppAccounts() method ended");
		return models;
	}

	/**
	 * This method is used to update Account Active Switch
	 */
	@Override
	public boolean updateAccountSw(String accId, String activeSw) {
		logger.debug("*** updateAccountSw() method started");
		String fileName=null,mailSub=null,mailBody=null,password=null;
		try {
			// load existing record using accId
			AppAccountEntity entity = appAccRepository.findById(Integer.parseInt(accId)).get();
			if (entity != null) {
				// Setting Account Active Sw (Y|N)
				entity.setActiveSw(activeSw);
				// Updating Account
				appAccRepository.save(entity);
				logger.debug("*** updateAccountSw() method ended");

				AppAccount accModel = new AppAccount();
				BeanUtils.copyProperties(entity, accModel);

				// TODO:Need to complete email functionality
				if (activeSw.equals("Y")) {
					// send Email saying account activated
					try {
						//get file name
						fileName = appProperties.getProperties().get(AppConstants.ACTIVATE_EMAIL_FILE);
						//get mail subject
						mailSub = appProperties.getProperties().get(AppConstants.ACTIVATE_EMAIL_SUB);
						//decrypt the password
						password=PasswordUtils.decrypt(accModel.getPassword());
						//set decrypted password to accModel object password field
						accModel.setPassword(password);
						//get email body
						mailBody = getEmailBodyContent(accModel, fileName);
						//send email to activate registered cw/admin
						emailUtils.sendEmail(entity.getEmail(), mailSub, mailBody);
					} catch (Exception e) {
					logger.error("Email Sending is failed : " + e.getMessage());
					}
					return true;
				} else {
					
					try {
						// send Email saying account de-activated
						// send account de-activation mail to registered case worker/admin
						// get file name
						fileName = appProperties.getProperties().get(AppConstants.DE_ACTIVATE_EMAIL_FILE);
						// get email subject
						mailSub = appProperties.getProperties().get(AppConstants.DE_ACTIVATE_EMAIL_SUB);
						// get email body content
						mailBody = getDeActivateAccountEmailBodyContent(fileName,accModel);
						// send email to registered cw/admin
						emailUtils.sendEmail(entity.getEmail(),mailSub,mailBody);
					}catch(Exception e) {
						logger.error("Email Sending is failed : " + e.getMessage());
					}
					return true;
				}				
			}
		} catch (Exception e) {
			logger.error("Exception occured in updateAccountSw() method : " + e.getMessage());
		}
		return false;
	}//method
	/**
	 * This method is used to get inactive account email body content from file
	 * @param fileName
	 * @param cwModel
	 * @return body
	 * @throws IOException
	 */
	public String getDeActivateAccountEmailBodyContent(String fileName,AppAccount accModel) throws IOException {
		//create BufferReader object
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		//create StringBuffer object
		StringBuffer body = new StringBuffer();
		//read the line from the text file
		String line = br.readLine();
		while (line != null) {
			if (line != null && !"".equals(line) && !"<br/>".equals(line)) {
				// process the line
				if (line.contains("USER_NAME")) {
					line = line.replace("USER_NAME", accModel.getFirstName() + " " + accModel.getLastName());
				}
				// Adding processed line to StringBuffer body
				body.append(line);
			}
			// read next line
			line = br.readLine();
		}
		// close/release br object
		br.close();
		return body.toString();
	}//method

	@Override
	public boolean registerPlan(AppPlan appplan) {
		logger.debug("register plan service started");
		//crete entity class object
		PlanDetalisEntity entity=new PlanDetalisEntity();
		//convert model to entity
		BeanUtils.copyProperties(appplan, entity);
		entity.setActive_sw(AppConstants.ACTIVE_SW);
		 entity=appPlanRepository.save(entity);
		logger.debug("registerplaan method ended");
		return (entity.getPlanId()>0)? true:false;
	}
	/*
	 * @Override public String findByName(String planName) { PlanDetalisEntity
	 * entity=appPlanRepository.findByName(planName); return (entity==null) ?
	 * "Unique" : "Duplicate";
	 * 
	 * }
	 */

	@Override
	public List<AppPlan> findAllAppPlans() {
		logger.debug("findallapps method is started");
		List<AppPlan> entities=new ArrayList<AppPlan>();
		try {
		List<PlanDetalisEntity> models=appPlanRepository.findAll();
		if(models.isEmpty()) {
			logger.warn("records not found");
		}else {
			for(PlanDetalisEntity entity:models) {
				AppPlan model=new AppPlan();
				BeanUtils.copyProperties(entity, model);
				entities.add(model);
			}
			logger.info("finadall method is loaded");
		}
		}catch(Exception e) {
			logger.error("error occured in findallall"+e.getMessage());
		
		}
		return entities;
	}

	@Override
	public boolean updatePlanSw(String planId, String Activesw) {
		logger.debug("update method is started");
		PlanDetalisEntity entity=appPlanRepository.findById(Integer.parseInt(planId)).get();
		if(entity!=null) {
			entity.setActive_sw(Activesw);
			appPlanRepository.save(entity);
			//coveet entity to model
			AppPlan model=new AppPlan();
			BeanUtils.copyProperties(entity, model);
			return true;
		}
			return false;

		
		
		
	}
}//class
