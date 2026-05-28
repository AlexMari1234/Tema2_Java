package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.AccountFactory;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Currency;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public class AccountFactoryTest {

	@Test
	public void testCreateSavingAccount() {
		Account account = AccountFactory.createSavingAccount(1, 1000.0);

		assertNotNull(account);
		assertTrue(account instanceof SavingAccount);
		assertEquals(1, account.getId());
		assertEquals(1000.0, account.getBalance(), 0);
		assertEquals(1000.0, account.maximumAmountToWithdraw(), 0);
		assertEquals(AbstractAccount.SAVING_ACCOUNT_TYPE, ((SavingAccount) account).getType());
	}

	@Test
	public void testCreateSavingAccountWithCurrency() {
		Currency usd = new Currency("USD");
		Account account = AccountFactory.createSavingAccount(2, 500.0, usd);

		assertTrue(account instanceof SavingAccount);
		assertEquals("USD", ((SavingAccount) account).getCurrency().getCode());
	}

	@Test
	public void testCreateCheckingAccount() throws NotEnoughFundsException {
		Account account = AccountFactory.createCheckingAccount(3, 1000.0, 100.0);

		assertNotNull(account);
		assertTrue(account instanceof CheckingAccount);
		assertEquals(3, account.getId());
		assertEquals(1000.0, account.getBalance(), 0);
		assertEquals(1100.0, account.maximumAmountToWithdraw(), 0);
		assertEquals(AbstractAccount.CHECKING_ACCOUNT_TYPE, ((CheckingAccount) account).getType());

		// behaviour smoke test: withdrawing into overdraft works
		account.withdraw(1050.0);
		assertEquals(-50.0, account.getBalance(), 0);
	}

	@Test
	public void testCreateCheckingAccountWithCurrency() {
		Currency eur = new Currency("EUR");
		Account account = AccountFactory.createCheckingAccount(4, 200.0, 50.0, eur);

		assertTrue(account instanceof CheckingAccount);
		assertEquals("EUR", ((CheckingAccount) account).getCurrency().getCode());
	}

	@Test
	public void testCreateAccountByTypeSaving() {
		Account account = AccountFactory.createAccount(
				AbstractAccount.SAVING_ACCOUNT_TYPE, 5, 250.0, 0.0);
		assertTrue(account instanceof SavingAccount);
		assertEquals(250.0, account.getBalance(), 0);
	}

	@Test
	public void testCreateAccountByTypeChecking() {
		Account account = AccountFactory.createAccount(
				AbstractAccount.CHECKING_ACCOUNT_TYPE, 6, 250.0, 100.0);
		assertTrue(account instanceof CheckingAccount);
		assertEquals(350.0, account.maximumAmountToWithdraw(), 0);
	}

	@Test
	public void testCreateAccountUnknownTypeThrows() {
		try {
			AccountFactory.createAccount(999, 7, 100.0, 0.0);
			fail("Expected IllegalArgumentException for unknown account type");
		} catch (IllegalArgumentException expected) {
			// ok
		}
	}
}

