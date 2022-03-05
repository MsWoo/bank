package com.bank.service;

import com.bank.entity.BankUser;
import com.bank.repository.BankUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BankUserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("UserDetailsServiceImpl.loadUserByUsername :::: {}", email);

		BankUser user = repo.findByEmail(email);

		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("Invalid username, please check user info !");
		}

		return user;
	}

}
