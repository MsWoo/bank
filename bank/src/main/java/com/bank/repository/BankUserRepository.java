package com.bank.repository;

import com.bank.entity.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankUserRepository extends JpaRepository<BankUser, Long> {
	public BankUser findByUsername(String username);

	public BankUser findByEmail(String email);
}
