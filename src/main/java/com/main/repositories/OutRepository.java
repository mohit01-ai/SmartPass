package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Outsider;

public interface OutRepository extends JpaRepository<Outsider, Integer> {
	Outsider findByMobileNo(String mobileNo);

}
