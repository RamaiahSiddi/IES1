package com.usa.ri.gov.ies.admin.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usa.ri.gov.ies.admin.entity.PlanDetalisEntity;
@Repository("appPlanRepository")
public interface AppPlanRepository extends JpaRepository<PlanDetalisEntity, Serializable> {
	
	//@Query(name="from PlanDetalisEntity where planName=:planame")
	//public PlanDetalisEntity findByName(String planame);

}
