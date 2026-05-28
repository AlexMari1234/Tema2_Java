package com.luxoft.bankapp.domain;

import java.io.Serializable;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.utils.Params;

public abstract class AbstractAccount implements Account, Serializable, Cloneable {

	private static final long serialVersionUID = -2272551373694344386L;

	/** Discriminators used by {@link AccountFactory} and {@link #parse(Params)} — NOT stored on instances. */
	public static final int SAVING_ACCOUNT_TYPE = 1;
	public static final int CHECKING_ACCOUNT_TYPE = 2;

	private int id;
	private double balance;

	public AbstractAccount(int id, double amount) {
		this.id = id;
		this.balance = amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	protected void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}

		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}

		this.balance -= amount;
	}

	/** Polymorphic — each concrete account type provides its own rule. */
	@Override
	public abstract double maximumAmountToWithdraw();

	@Override
	public long decimalValue() {
		return Math.round(balance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		return id == other.id;
	}

	public static Account parse(Params params) {
		switch (params.get("accountType")) {
			case "s": return SavingAccount.parse(params);
			case "c": return CheckingAccount.parse(params);
		}
		return null;
	}

	@Override
	public AbstractAccount clone() throws CloneNotSupportedException {
		return (AbstractAccount) super.clone();
	}
}
