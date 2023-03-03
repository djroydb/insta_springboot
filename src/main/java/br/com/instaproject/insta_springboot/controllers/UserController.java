package br.com.instaproject.insta_springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instaproject.insta_springboot.model.User;
import br.com.instaproject.insta_springboot.repository.UserRepository;
import jakarta.persistence.PostUpdate;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> findAll(){
		
		List<User> users = repository.findAll();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user =  repository.findById(id).get();
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> insert(@RequestBody User user) {
		User response = repository.save(user);
		return ResponseEntity.ok(response);
	}
	
	@PostUpdate
	public ResponseEntity<User> update(User user) {
		User entity = repository.findById(user.getId()).get();
		
		entity.setEmail(user.getEmail());
		entity.setName(user.getName());
		entity.setLastName(user.getLastName());
		entity.setNickName(user.getNickName());
		entity.setPassword(user.getPassword());
		
		User response =  repository.save(entity);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
		User entity = repository.findById(id).get();
		repository.delete(entity);
		return ResponseEntity.ok("Usuario " + entity.getName() + " removido com sucesso");
		
	}
	
}
