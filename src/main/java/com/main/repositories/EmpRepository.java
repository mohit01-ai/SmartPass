package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Employee;

public interface EmpRepository extends JpaRepository<Employee,Integer> {
      Employee findByEmployeeId(String employeeId);  
      
       
}
 