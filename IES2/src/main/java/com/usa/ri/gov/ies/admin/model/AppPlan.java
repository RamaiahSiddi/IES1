package com.usa.ri.gov.ies.admin.model;

import java.util.Date;

import lombok.Data;

@Data
public class AppPlan {
private Integer planId;

	
	private String planName;

	
	private String plandes;

	
	private String startdate;

	
	private String enddate;

	private Date create_dt;

	private Date update_dt;
	
	private String active_sw;

}
