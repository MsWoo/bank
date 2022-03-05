package com.bank.service;

import java.util.List;

import javax.transaction.Transactional;

import com.bank.entity.BankUser;
import com.bank.repository.BankUserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final BankUserRepository repo;

	@Transactional
	public void joinUser(BankUser user) {
		repo.save(user);
	}

	public List<BankUser> findAll() {
		return repo.findAll();
	}

}
