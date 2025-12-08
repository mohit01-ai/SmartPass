package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByEmail(String email);

}
