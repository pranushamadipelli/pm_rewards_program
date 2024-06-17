package com.charter.customer.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.charter.customer.dto.CustomerDTO;
import com.charter.customer.dto.CustomerRewardsInfoResponse;
import com.charter.customer.dto.TransactionDTO;
import com.charter.customer.execption.CustomerException;
import com.charter.customer.model.Customer;
import com.charter.customer.model.Transaction;
import com.charter.customer.repository.CustomerRepository;
import com.charter.customer.service.CustomerBusinessService;
import com.charter.customer.utils.LogUtils;
import com.charter.customer.utils.RewardsProgramUtils;

@Service
public class CustomerBusinessServiceImpl implements CustomerBusinessService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Value("${chaeter.max.transcation}")
    private long maxTransactionAmount;
	
	@Value("${chaeter.min.transcation}")
    private long minTransactionAmount;
	
	@Value("${chaeter.min.rewardpoints}")
    private long minRewardPoints;

	@Value("${chaeter.min.rewardpoints}")
    private long maxRewardPoints;

	/**
	 * 
	 */
	private Function<CustomerDTO, Customer> _buildCustomer = (dto) -> {
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setName(dto.getName());
		customer.setPhoneNumber(dto.getPhoneNumber());
		customer.setEmail(dto.getEmail());
		return customer;
	};

	@Override
	public Customer storeCustomer(CustomerDTO dto) throws CustomerException {
		Customer customer = null;
		try {
			LogUtils.startInfoLog();
			if (dto != null && dto.getName() != null) {
				customer = _buildCustomer.apply(dto);
				if (CollectionUtils.isNotEmpty(dto.getTransactions())) {
					customer.setTransactions(updateTransactions(dto.getTransactions(), customer));
				}
				customer = customerRepository.save(customer);
			}
			LogUtils.endDeInfoLog();
		} catch (Exception e) {
			LogUtils.errorLog(e);
		}
		return customer;

	}


	/**
	 * 
	 * @param transactionDTOs
	 * @param customer
	 * @return
	 */
	public List<Transaction> updateTransactions(List<TransactionDTO> transactionDTOs, Customer customer) {
		try {
			
			return transactionDTOs.stream().map(transactionDTO -> {
				Transaction transaction = new Transaction();
				transaction.setAmount(transactionDTO.getAmount());
				transaction.setId(transactionDTO.getId());
				transaction.setTransactionDateTime(transactionDTO.getTransactionDateTime());
				transaction.setCustomer(customer);
				transaction.setRewardPoints(RewardsProgramUtils.calculateRewardPoints(transaction.getAmount(),maxTransactionAmount, minTransactionAmount,maxRewardPoints,minRewardPoints));
				return transaction;
			}).collect(Collectors.toList());
			
		} catch (Exception e) {
			LogUtils.errorLog(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<CustomerRewardsInfoResponse> getCustomerRewardsInfo(String startDate, String endDate)
			throws CustomerException {
		LogUtils.startInfoLog();
		Iterable<Customer> customers = null;
		Map<String, Long> monthlyRewards = null;
		CustomerRewardsInfoResponse response = null;
		List<CustomerRewardsInfoResponse> customerRewardsInfos = new ArrayList<CustomerRewardsInfoResponse>();
		try {
			customers = customerRepository.findTransactions(convert(startDate), convert(endDate));
			if (customers != null) {
				for (Customer customer : customers) {
					response = new CustomerRewardsInfoResponse();
					monthlyRewards = getTransactiondata(customer);
					response.setCustomerName(customer.getName());
					response.setMonthOfRewards(monthlyRewards);
					response.setTotalRewards(monthlyRewards.values().stream().mapToLong(Long::longValue).sum());
					customerRewardsInfos.add(response);
				}
			}

		} catch (Exception e) {
			LogUtils.errorLog(e);
		}
		LogUtils.endDeInfoLog();
		return customerRewardsInfos;
	}
	
	private LocalDateTime convert(String date) {
		LocalDateTime localdateTime =  null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			localdateTime = LocalDateTime.parse(date, formatter);
		} catch (Exception e) {
			LogUtils.errorLog(e);
		}
		return localdateTime;
	}

	/**
	 * 
	 * @param customer
	 * @return
	 */
	private Map<String, Long> getTransactiondata(Customer customer) {
		Map<String, Long> monthlyRewards = null;
		try {
			monthlyRewards = new HashMap<>();
			if(CollectionUtils.isNotEmpty(customer.getTransactions())) {
			for (Transaction transaction : customer.getTransactions()) {
				Month month = transaction.getTransactionDateTime().getMonth();
				monthlyRewards.put(month.toString(),
						monthlyRewards.getOrDefault(month, 0l) + transaction.getRewardPoints());
			}
			}
		} catch (Exception e) {
			LogUtils.errorLog(e);
		}

		return monthlyRewards;
	}
	
	
	


}
