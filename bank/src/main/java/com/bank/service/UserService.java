package com.bank.service;

import java.util.List;

import javax.transaction.Transactional;

import com.bank.entity.BankUser;
import com.bank.entity.UserRole;
import com.bank.repository.BankUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final BankUserRepository repo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public void joinUser(BankUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.addUserRole(UserRole.ROLE_USER);
		// user.addUserRole(UserRole.ROLE_ADMIN);
		user.setFromSocial("bankClient");
		user.setBalance((long) 0);
		repo.save(user);
	}

	public List<BankUser> findAll() {
		return repo.findAll();
	}

	public void deposit(BankUser user, Long money) {
		user.setBalance(user.getBalance() + money);
		repo.save(user);
	}

	public void withdraw(BankUser user, Long money) {
		user.setBalance(user.getBalance() - money);
		repo.save(user);
	}

	public void transfer(BankUser user, String target, Long money) {
		BankUser targetUser = repo.findByEmail(target);
		user.setBalance(user.getBalance() - money);
		targetUser.setBalance(targetUser.getBalance() + money);
		repo.save(user);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

}
