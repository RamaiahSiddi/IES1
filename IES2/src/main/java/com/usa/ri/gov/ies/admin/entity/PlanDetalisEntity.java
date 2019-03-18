package com.usa.ri.gov.ies.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name="PLAN_DTS")
@Entity( name="planDetailsRepository")
public class PlanDetalisEntity {
	@Id
	@GeneratedValue
	@PrimaryKeyJoinColumn
	@Column(name = "PLAN_ID")
	private Integer planId;

	@Column(name = "PLANNAME",unique=true)
	private String planName;

	@Column(name = "PLANDES")
	private String plandes;

	@Column(name = "START_DATE")
	private String startdate;

	@Column(name = "END_DATE")
	private String enddate;

	@CreationTimestamp
	private Date create_dt;
	@UpdateTimestamp
	private Date update_dt;
	@Column(name="ACTIVE_SW")
	private String active_sw;

}
