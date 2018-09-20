package com.springboot.oauthexample.resource;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.oauthexample.dao.UserDAO;
import com.springboot.oauthexample.entities.Response;
import com.springboot.oauthexample.entities.Role;
import com.springboot.oauthexample.entities.User;
import com.springboot.oauthexample.exception.CustomException;
import com.springboot.oauthexample.repository.RoleRepository;
import com.springboot.oauthexample.repository.UserRepository;
import com.springboot.oauthexample.service.CustomUserDetailsService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/{userName}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String getUser(@PathVariable(value = "userName") String userName) throws CustomException {
		return "Hello! This is user.";
//		return userRepository.findByUsername(userName).orElseThrow(() -> new CustomException(userName + " not found."));
	}

	@GetMapping("/admin/{userName}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User getAdmin(@PathVariable(value = "userName") String userName) throws CustomException {
		return userRepository.findByUsername(userName).orElseThrow(() -> new CustomException(userName + " not found."));
	}

	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private Response login(@RequestBody User user, HttpServletRequest request) {
		Response response = new Response();
		try {
			HttpSession session = request.getSession();
			User userData = userDAO.logIn(user);
			if (null != userData && user.getPassword().equals(userData.getPassword())) {
				session.setAttribute("user", userData);
				response.setStatus("200");
				response.setMessage("Login Successfull.");
				userData.setPassword(null);
				response.setResult(userData);
			} else {
				response.setStatus("300");
				response.setMessage("Invalid Credentials.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("500");
			response.setMessage("Couldn't perform the request.");
			response.setResult(e.getMessage());
		}
		return response;
	}

	 @RequestMapping(value = "/registration", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response createNewUser(@RequestBody User user) {
		Response response= new Response();
		System.out.println(user.getUsername());
		Optional<User> userExists = userRepository.findByUsername(user.getUsername());
		if (userExists.isPresent()) {
			response.setErrorText("User already exist.");
		} else {
			
			Set<Role> role = roleRepository.findByName(user.getRoles().iterator().next().getName());
			user.setRoles(role);
			userService.saveUser(user);
			response.setMessage("User has been registered successfully");
			response.setStatus("200");
			response.setResult(user);
		}
		return response;
	}
	
	
}
