package com.charter.customer.utils;

public class RewardsProgramUtils {

	public static long calculateRewardPoints(double transactionAmount, long maxTransactionAmount,
			long minTransactionAmount, long maxRewardPoints, long minRewardPoints) {
		long points = 0;
		try {

			if (transactionAmount > maxTransactionAmount) {
				points += (transactionAmount - maxTransactionAmount) * maxRewardPoints;
				transactionAmount = maxTransactionAmount;
			}
			if (transactionAmount > minTransactionAmount) {
				points += (transactionAmount - minTransactionAmount)*minRewardPoints;
			}

		} catch (Exception e) {
			LogUtils.errorLog(e);
		}
		return points;
	}

}
