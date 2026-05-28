package com.luxoft.bankapp.domain;

/**
 * Static factory for creating {@link Account} instances
 * ({@link SavingAccount} / {@link CheckingAccount}).
 *
 * <p>This class is not meant to be instantiated.</p>
 */
public final class AccountFactory {

	private AccountFactory() {
		// prevent instantiation
	}

	/**
	 * Creates a {@link SavingAccount}.
	 */
	public static Account createSavingAccount(int id, double balance) {
		return new SavingAccount(id, balance);
	}

	/**
	 * Creates a {@link SavingAccount} with currency.
	 */
	public static Account createSavingAccount(int id, double balance, Currency currency) {
		return new SavingAccount(id, balance, currency);
	}

	/**
	 * Creates a {@link CheckingAccount}.
	 */
	public static Account createCheckingAccount(int id, double balance, double overdraft) {
		return new CheckingAccount(id, balance, overdraft);
	}

	/**
	 * Creates a {@link CheckingAccount} with currency.
	 */
	public static Account createCheckingAccount(int id, double balance, double overdraft, Currency currency) {
		return new CheckingAccount(id, balance, overdraft, currency);
	}

	/**
	 * Creates an account based on a type discriminator.
	 *
	 * @param type      {@link AbstractAccount#SAVING_ACCOUNT_TYPE} or
	 *                  {@link AbstractAccount#CHECKING_ACCOUNT_TYPE}
	 * @param id        account id
	 * @param balance   initial balance
	 * @param overdraft only used for checking accounts; ignored for saving
	 * @return a newly created {@link Account}
	 * @throws IllegalArgumentException if the type is unknown
	 */
	public static Account createAccount(int type, int id, double balance, double overdraft) {
		switch (type) {
			case AbstractAccount.SAVING_ACCOUNT_TYPE:
				return createSavingAccount(id, balance);
			case AbstractAccount.CHECKING_ACCOUNT_TYPE:
				return createCheckingAccount(id, balance, overdraft);
			default:
				throw new IllegalArgumentException("Unknown account type: " + type);
		}
	}
}

