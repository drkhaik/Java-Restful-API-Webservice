package com.in28minutes.rest.webservices.restful_webservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restful_webservice.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	
}
