package com.main.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.entities.Admin;
import com.main.entities.Employee;
import com.main.entities.Outsider;
import com.main.services.MyServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {

	@Autowired
	private MyServices myServ;

	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@GetMapping("/openEmpLoginPage")
	public String openEmpLoginPage(Model model) {
		model.addAttribute("employee", new Employee());
		return "employeeLogin";
	}

	@PostMapping("/employeeLogin")
	public String getEmpLoginDetails(@ModelAttribute("employee") Employee emp, Model model) {
		boolean status = myServ.getLoginDetails(emp.getEmployeeId(), emp.getPassword());
		if (status) {
			String gatePassId = "EMP-GP-" + (int) (Math.random() * 100000);
			String date = LocalDate.now().toString();
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Current time

			model.addAttribute("type", "Employee");
			model.addAttribute("name", emp.getName());
			model.addAttribute("employeeId", emp.getEmployeeId());
			model.addAttribute("date", date);
			model.addAttribute("time", time); // Pass time
			model.addAttribute("gatePassId", gatePassId);

			return "genGatePass";
		} else {
			model.addAttribute("errorMsg", "You are not eligible for gate pass");
			return "employeeLogin";
		}
	}

	@GetMapping("/openOutsiderPage")
	public String openOutsidersPage(Model model) {
		model.addAttribute("outsider", new Outsider());
		return "outsider";
	}

	@PostMapping("/outsiderLogin")
	public String getOutsiderDetails(@ModelAttribute("outsider") Outsider out, Model model) {
		boolean status = myServ.getOutsiderDetails(out.getMobileNo(), out.getCode());
		if (status) {
			String gatePassId = "OUT-GP-" + (int) (Math.random() * 100000);
			String date = LocalDate.now().toString();
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")); // Current time

			model.addAttribute("type", "Outsider");
			model.addAttribute("name", out.getName());
			model.addAttribute("mobileNo", out.getMobileNo());
			model.addAttribute("purpose", out.getPurpose());
			model.addAttribute("date", date);
			model.addAttribute("time", time); // Pass time
			model.addAttribute("gatePassId", gatePassId);

			return "genGatePass";
		} else {
			model.addAttribute("errorMsg", "You are not eligible for gate pass");
			return "outsider";
		}
	}

	@GetMapping("/openAdminLoginPage")
	public String openAdminLoginPage(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminLogin";
	}

	@PostMapping("/adminLoginPage")
	public String getAdminLoginDetails(@ModelAttribute("admin") Admin admin, Model model, HttpSession session) {
		boolean status = myServ.getAdminDetails(admin.getEmail(), admin.getPassword());

		if (status) {
			session.setAttribute("adminEmail", admin.getEmail());

			model.addAttribute("successMsg", "Admin Login Successfully");
			return "add_removeGatePassers";
		} else {
			model.addAttribute("errorMsg", "You are not an admin");
			return "adminLogin";
		}
	}

	@GetMapping("/openEmpRegistrationPage")
	public String openEmpRegistrationPage(Model model) {
		model.addAttribute("employee", new Employee());
		return "employeeRegistration";
	}

	@PostMapping("/employeeRegistration")
	public String submitEmpRegistrationDetail(@ModelAttribute("employee") Employee emp, Model model) {
		boolean status = myServ.submitEmpRegistrationDetails(emp);

		if (status) {
			model.addAttribute("successMsg", "Employee Gate pass added successfully");
		} else {
			model.addAttribute("errorMsg", "Employee Gate pass addition unsuccessfull");

		}
		return "employeeRegistration";
	}

	@GetMapping("/openOutRegistrationPage")
	public String openOutRegistrationPage(Model model) {
		model.addAttribute("outsider", new Outsider());
		return "outsiderRegistration";
	}

	@PostMapping("/outsiderRegistration")
	public String submitOutRegistrationDetail(@ModelAttribute("outsider") Outsider out, Model model) {
		boolean status = myServ.submitOutRegistrationDetails(out);

		if (status) {
			model.addAttribute("successMsg", "Outsider Gate pass added successfully");
		} else {
			model.addAttribute("errorMsg", "Outsider Gate pass addition unsuccessfull");

		}
		return "outsiderRegistration";
	}

	@GetMapping("/openEmpRemovePage")
	public String openEmpDeletePage(Model model, HttpSession session) {
		model.addAttribute("employee", new Employee());

		Admin admin = new Admin();
		String email = (String) session.getAttribute("adminEmail");
		if (email != null) {
			admin.setEmail(email);
		}
		model.addAttribute("admin", admin);

		return "employeeRemove";
	}

	@PostMapping("/employeeRemove")
	public String submitEmpDeleteDetail(@ModelAttribute("employee") Employee emp, Model model) {
		String empId = emp.getEmployeeId();
		boolean status = myServ.deleteEmpDetails(empId);

		if (status) {
			model.addAttribute("successMsg", "Employee removed from gate pass entry");
		} else {
			model.addAttribute("errorMsg", "Employee Gate pass Removel unsuccessfull! Data might not exist");

		}
		model.addAttribute("employee", new Employee());
		model.addAttribute("admin", new Admin());
		return "employeeRemove";
	}

	@GetMapping("/openOutRemovePage")
	public String openOutDeletePage(Model model, HttpSession session) {
		model.addAttribute("outsider", new Outsider());
		Admin admin = new Admin();
		String email = (String) session.getAttribute("adminEmail");
		if (email != null) {
			admin.setEmail(email);
		}
		model.addAttribute("admin", admin);
		return "outsiderRemove";
	}

	@PostMapping("/outsiderRemove")
	public String submitOutDeleteDetail(@ModelAttribute("outsider") Outsider out, Model model) {
		String outMob = out.getMobileNo();
		boolean status = myServ.deleteOutDetails(outMob, out.getCode());

		if (status) {
			model.addAttribute("successMsg", "Outsider removed from gate pass entry");
		} else {
			model.addAttribute("errorMsg", "Outsider Gate pass Removel unsuccessfull ! data might not exist");

		}
		return "outsiderRemove";
	}

	@PostMapping("/employeeRemoveAll")
	public String submitEmpRemoveAll(@ModelAttribute("admin") Admin adm, Model model) {
		boolean status = myServ.deleteAllEmployee(adm.getEmail(), adm.getPassword());
		model.addAttribute("employee", new Employee());
		model.addAttribute("admin", new Admin());
		if (status) {
			model.addAttribute("successMsg", "All Employees removed from gate pass entry");
		} else {
			model.addAttribute("errorMsg", "All Employee Gate pass Removal unsuccessful! Data might not exist");
		}
		return "employeeRemove";
	}

	@PostMapping("/outsidersRemoveAll")
	public String submitOutRemoveAll(@ModelAttribute("admin") Admin adm, Model model) {
		boolean status = myServ.deleteAllOutsiders(adm.getEmail(), adm.getPassword());
		model.addAttribute("outsider", new Outsider());
		model.addAttribute("admin", new Admin());
		if (status) {
			model.addAttribute("successMsg", "All Outsiders removed from gate pass entry");
		} else {
			model.addAttribute("errorMsg", "All Outsiders Gate pass Removal unsuccessful! Data might not exist");
		}
		return "outsiderRemove";
	}

}
