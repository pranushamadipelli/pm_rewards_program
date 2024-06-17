package com.charter.customer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.charter.customer.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Query(value = "SELECT DISTINCT c.* " + "FROM customer c " + "LEFT JOIN transaction t ON c.id = t.customer_id "
			+ "WHERE t.transaction_date_time BETWEEN ? AND ?", nativeQuery = true)
	List<Customer> findTransactions(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

}
