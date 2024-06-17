package com.charter.customer.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CustomerDTO {
	
	@Positive
	@JsonProperty("id")
	private long id;
	
	@NotEmpty
	@JsonProperty( "name")
	private String name;
	
	
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	@Email
	@JsonProperty("email")
	private String email;

	@NotEmpty
	private List<TransactionDTO> transactions;

}
