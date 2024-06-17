package com.charter.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.charter.customer.utils.RewardsProgramUtils;


class RewardPointsCalculatorTest {


	@Test
	public void testCalculateRewardPoints_Over100() {
		long points = RewardsProgramUtils.calculateRewardPoints(120, 100, 50,2, 1);
		assertEquals(90, points);
	}

	@Test
	public void testCalculateRewardPoints_Between50And100() {
		long points =  RewardsProgramUtils.calculateRewardPoints(80, 100, 50,2, 1);
		assertEquals(30, points);
	}

	@Test
	public void testCalculateRewardPoints_LessThan50() {
		long points =  RewardsProgramUtils.calculateRewardPoints(40, 100, 50,2, 1);
		assertEquals(0, points);
	}

}
