package com.bank.controller;

import com.bank.entity.BankUser;
import com.bank.entity.UserRole;
import com.bank.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BankController {

	private final UserService service;

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/loginPage")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/signup")
	public String signUpForm() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signUp(BankUser user) {
		user.addUserRole(UserRole.ROLE_USER);
		// user.addUserRole(UserRole.ROLE_ADMIN);
		user.setFromSocial("");
		service.joinUser(user);
		return "redirect:/login";
	}

	@RequestMapping("/user")
	public String test() {
		return "user";
	}

}
