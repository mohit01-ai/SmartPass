package com.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entities.Admin;
import com.main.entities.Employee;
import com.main.entities.Outsider;
import com.main.repositories.AdminRepository;
import com.main.repositories.EmpRepository;
import com.main.repositories.OutRepository;

@Service
public class MyServicesImpl implements MyServices {

	@Autowired
	private EmpRepository empRepo;

	@Autowired
	private OutRepository outRepo;

	@Autowired
	private AdminRepository adminRepo;

	public boolean getLoginDetails(String empId, String password) {
		Employee validEmp = empRepo.findByEmployeeId(empId);

		if (validEmp != null && validEmp.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getOutsiderDetails(String mobileNo, String code) {
		Outsider validOutsider = outRepo.findByMobileNo(mobileNo);

		if (validOutsider != null && validOutsider.getCode().equals(code)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getAdminDetails(String email, String password) {
		Admin validAdmin = adminRepo.findByEmail(email);

		if (validAdmin != null && validAdmin.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean submitEmpRegistrationDetails(Employee emp) {

		try {
			empRepo.save(emp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean submitOutRegistrationDetails(Outsider out) {
		try {
			outRepo.save(out);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEmpDetails(String empId) {
		Employee validEmp = empRepo.findByEmployeeId(empId);
		if (validEmp != null) {
			empRepo.delete(validEmp);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteOutDetails(String mobileNo, String code) {
		Outsider validOut = outRepo.findByMobileNo(mobileNo);

		if (validOut != null && validOut.getCode().equals(code)) {
			outRepo.delete(validOut);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteAllEmployee(String email, String adminPass) {
		try {
			Admin adm = adminRepo.findByEmail(email);
			if (adm != null && adm.getPassword().equals(adminPass)) {
				empRepo.deleteAll();
				return true;
			} else {
				System.out.println("Invalid admin credentials!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAllOutsiders(String email, String password) {
		try {
			Admin adm = adminRepo.findByEmail(email);
			if (adm != null && adm.getPassword().equals(password)) {
				outRepo.deleteAll();
				return true;
			} else {
				System.out.println("Invalid admin credentials!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
