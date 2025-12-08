package com.main.services;

import org.springframework.stereotype.Service;

import com.main.entities.Employee;
import com.main.entities.Outsider;

@Service
public interface MyServices {
    
	public boolean getLoginDetails(String empId,String password);
	public boolean getOutsiderDetails(String mobileNo,String code);
	public boolean getAdminDetails(String email,String password);
	public boolean submitEmpRegistrationDetails(Employee emp);
	public boolean submitOutRegistrationDetails(Outsider out);
	public boolean deleteEmpDetails(String empId);
	public boolean deleteOutDetails(String mobileNo,String code);
	public boolean deleteAllEmployee(String email,String password);
	public boolean deleteAllOutsiders(String email,String password);
}
