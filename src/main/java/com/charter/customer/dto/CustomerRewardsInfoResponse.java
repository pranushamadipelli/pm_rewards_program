package com.charter.customer.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
public class CustomerRewardsInfoResponse {
	
	@JsonProperty("customer_name")
	private String customerName;
	
	@JsonProperty("month_of_rewards")
	private  Map<String, Long>  monthOfRewards;
	
	@JsonProperty("total_rewards")
	private long totalRewards;
	
}
