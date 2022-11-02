package com.alpey.invoice.feature.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.alpey.invoice.feature.company.Company;
import com.alpey.invoice.feature.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	@OneToOne
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	@JsonIgnore
	private Company company;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Customer> customers;

}
