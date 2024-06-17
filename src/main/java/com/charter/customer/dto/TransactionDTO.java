package com.charter.customer.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionDTO {
	
	private long id;
	
	@NotEmpty
	@Positive
	@JsonProperty("amount")
	private double amount;
	
	@NotEmpty
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonProperty( "transaction_date_time")
	private LocalDateTime transactionDateTime;
	
	@JsonProperty("reward_points")
	private long rewardPoints;
	
}
