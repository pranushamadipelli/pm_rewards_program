package com.charter.customer.service;

import java.time.LocalDate;
import java.util.List;

import com.charter.customer.dto.CustomerDTO;
import com.charter.customer.dto.CustomerRewardsInfoResponse;
import com.charter.customer.execption.CustomerException;
import com.charter.customer.model.Customer;

public interface CustomerBusinessService {

	Customer storeCustomer(CustomerDTO customer) throws CustomerException;

	List<CustomerRewardsInfoResponse> getCustomerRewardsInfo(String startDate, String endDate) throws CustomerException; 

}
