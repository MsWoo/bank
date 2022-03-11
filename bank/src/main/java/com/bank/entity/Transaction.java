package com.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Transactions")

public class Transaction {
	@Id
	@GeneratedValue
	private Long id;

	private String date;

	@Column(name = "user_id")
	private Long uid;

	@Column(name = "transaction_amount")
	private Long amount;

	@Column(name = "transaction_type")
	private String type;

}
