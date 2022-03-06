package com.bank.controller;

import com.bank.entity.BankUser;
import com.bank.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BankController {

	private final UserService service;

	@RequestMapping("/")
	public String home(Model model, Authentication authentication) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				model.addAttribute("name", user.getUsername());
			}
		}
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
		service.joinUser(user);
		return "redirect:/loginPage";
	}

	@RequestMapping("/mypage")
	private String mypage(Model model, Authentication authentication) {
		// MyUserDetail
		if (authentication.getPrincipal() instanceof BankUser) {
			BankUser user = (BankUser) authentication.getPrincipal();
			model.addAttribute("email", user.getEmail());
			model.addAttribute("name", user.getUsername());
			model.addAttribute("balance", user.getBalance());
		}
		return "mypage";
	}

	@RequestMapping("/deposit")
	private String deposit(Model model, Authentication authentication) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				model.addAttribute("name", user.getUsername());
				model.addAttribute("balance", user.getBalance());
			}
		}
		return "deposit";
	}

	@PostMapping("/deposit")
	private String deposit_post(Model model, Authentication authentication,
			@RequestParam(value = "balance") Long money) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				service.deposit(user, money);
			}
		}
		return "redirect:/deposit";
	}

	@RequestMapping("/withdraw")
	private String withdraw(Model model, Authentication authentication) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				model.addAttribute("name", user.getUsername());
				model.addAttribute("balance", user.getBalance());
			}
		}
		return "withdraw";
	}

	@PostMapping("/withdraw")
	private String withdraw_post(Model model, Authentication authentication,
			@RequestParam(value = "balance") Long money) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				service.withdraw(user, money);
			}
		}
		return "redirect:/withdraw";
	}

	@RequestMapping("/transfer")
	private String transfer(Model model, Authentication authentication) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				model.addAttribute("name", user.getUsername());
				model.addAttribute("balance", user.getBalance());
			}
		}
		return "transfer";
	}

	@PostMapping("/transfer")
	private String transfer_post(Model model, Authentication authentication,
			@RequestParam(value = "target") String target,
			@RequestParam(value = "balance") Long money) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof BankUser) {
				BankUser user = (BankUser) authentication.getPrincipal();
				service.transfer(user, target, money);
			}
		}
		return "redirect:/transfer";
	}
}
