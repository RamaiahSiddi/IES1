package com.usa.ri.gov.ies.admin.model;

import java.util.Date;

import lombok.Data;

@Data
public class AppAccount {

	private Integer accId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String dob;

	private String gender;

	private Long ssn;

	private String phno;

	private String activeSw;

	private Date createDate;

	private Date updateDate;

	private String role;

}
