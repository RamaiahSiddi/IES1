package com.usa.ri.gov.ies.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "APP_ACCOUNTS")
public class AppAccountEntity {

	@Id
	@GeneratedValue
	@Column(name = "ACC_ID")
	private Integer accId;

	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "PWD")
	private String password;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "SSN")
	private Long ssn;

	@Column(name = "PHNO")
	private String phno;

	@Column(name = "ACTIVE_SW")
	private String activeSw;

	@Column(name = "ROLE")
	private String role;

	@CreationTimestamp
	@Column(name = "CREATE_DT")
	private Date createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DT")
	private Date updateDate;

}
