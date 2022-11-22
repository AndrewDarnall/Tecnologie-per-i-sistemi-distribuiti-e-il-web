package portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portal.domain.Result;
import portal.domain.User;
import portal.repository.UserRepository;
import portal.service.ResultService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResultService resultService;

	@GetMapping("/user/info")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> getCurrentUserInfo() {
		log.debug("GET on /user/info");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User info = userRepository.findByUsername(username).get();
		return ResponseEntity.ok().body(info);
	}

	@GetMapping("/user/list")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		log.debug("GET on /user/list");
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/users/unregistered")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<String>> getUnregisteredUsersWithResults() {
		log.debug("GET ON /users/unregistered");

		List<String> unregUsers = new ArrayList<>();

		resultService.getAllResults().stream().map(Result::getStudent).distinct().forEach(student -> {
			if (!userRepository.findByUsername(student).isPresent())
				unregUsers.add(student);
		});

		return ResponseEntity.ok().body(unregUsers);
	}

}
