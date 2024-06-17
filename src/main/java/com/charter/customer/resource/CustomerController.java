package com.charter.customer.resource;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charter.customer.aop.security.CharterSecured;
import com.charter.customer.aop.security.Role;
import com.charter.customer.dto.CustomerDTO;
import com.charter.customer.dto.CustomerRewardsInfoResponse;
import com.charter.customer.model.Customer;
import com.charter.customer.service.CustomerBusinessService;
import com.charter.customer.utils.LogUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull; 

@RestController
@Tag(name = "Customer Resource")
@RequestMapping("/api/v1/customer")
@CharterSecured(roles = {Role.ADMIN, Role.USER})
public class CustomerController {

	@Autowired
	private CustomerBusinessService customerBusinessService;

	/**
	 * 
	 * @param customerDTO
	 * @return
	 */
	 @Operation(summary = "Create a new customer", description = "This API creates a new customer and their transaction  with the provided details.")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "201", description = "User created successfully",
	                     content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
	        @ApiResponse(responseCode = "400", description = "Invalid input",
	                     content = @Content(schema = @Schema(implementation = Exception.class)))
	    })
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		Customer customer = null;
		try {
			customer = customerBusinessService.storeCustomer(customerDTO);
			return new ResponseEntity<>(customer, HttpStatus.CREATED);
		} catch (Exception e) {
			LogUtils.errorLog(e);
		}
		return null;
	}
	 
	 
	 @Operation(summary = "Retrive Customer Rewards Infomation", description = "This API Retrive customer earned rewards points based on date criteria .")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Retrive cutomer rewards info  successfully",
	                     content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
	        @ApiResponse(responseCode = "400", description = "Invalid input",
	                     content = @Content(schema = @Schema(implementation = Exception.class))),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = Exception.class))),
	        @ApiResponse(responseCode = "204", description = "No data found",
            content = @Content(schema = @Schema(implementation = Exception.class)))
	    })
	@GetMapping("/cutomer-rewards-info")
	public ResponseEntity<List<CustomerRewardsInfoResponse>> getCustomersRewardsInfo(@RequestParam @NotNull(message = "Start date must be provided") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @NotNull(message = "end date must be provided") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)String endDate) {
		List<CustomerRewardsInfoResponse> customerRewardsInfo = null;
		try {
			customerRewardsInfo = customerBusinessService.getCustomerRewardsInfo(startDate, endDate);

			if (customerRewardsInfo.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(customerRewardsInfo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	 @PostMapping("/tesr")
		public ResponseEntity<Customer> testustomer(@Valid @RequestBody CustomerDTO customerDTO) {
			Customer customer = null;
			try {
				customer = customerBusinessService.storeCustomer(customerDTO);
				return new ResponseEntity<>(customer, HttpStatus.CREATED);
			} catch (Exception e) {
				LogUtils.errorLog(e);
			}
			return null;
		}

}
