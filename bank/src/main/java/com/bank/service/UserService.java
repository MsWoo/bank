package com.bank.service;

import java.util.List;

import javax.transaction.Transactional;

import com.bank.entity.BankUser;
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
		repo.save(user);
	}

	public List<BankUser> findAll() {
		return repo.findAll();
	}

}
