package com.in28minutes.rest.webservices.restful_webservice.user;


import java.util.List;
import java.util.Optional;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_webservice.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJPAResource {
	
	private UserRepository userRepository;

	public UserJPAResource( UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// GET /users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	// GET /users
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
	
	    // Tạo liên kết tới tạo người dùng
	    WebMvcLinkBuilder createUserLink = linkTo(methodOn(this.getClass()).createUser(null)); // null vì đây là POST
	    entityModel.add(createUserLink.withRel("create-user"));

//	    String deleteUserUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//	            .path("/users/{id}")
//	            .buildAndExpand(id)
//	            .toUriString();
//	    entityModel.add(linkTo(deleteUserUrl).withRel("delete-user"));
		
		return entityModel;
	}
	
	//POST /users
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	//DELETE /users
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

}
