package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountCache;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;

public class AccountCacheTest {

	private AccountCache cache;

	@Before
	public void setUp() {
		cache = new AccountCache();
		cache.load();
	}

	@Test
	public void testCloneSavingAccount() throws CloneNotSupportedException {
		AbstractAccount account = cache.cloneAccount(AccountCache.SAVING);

		assertNotNull(account);
		assertTrue(account instanceof SavingAccount);
		assertEquals(0, account.getId());
		assertEquals(0.0, account.getBalance(), 0);
	}

	@Test
	public void testCloneCheckingAccount() throws CloneNotSupportedException {
		AbstractAccount account = cache.cloneAccount(AccountCache.CHECKING);

		assertNotNull(account);
		assertTrue(account instanceof CheckingAccount);
		assertEquals(0, account.getId());
		assertEquals(0.0, account.getBalance(), 0);
		assertEquals(0.0, ((CheckingAccount) account).getOverdraft(), 0);
	}

	@Test
	public void testCloneReturnsDifferentInstances() throws CloneNotSupportedException {
		AbstractAccount a1 = cache.cloneAccount(AccountCache.SAVING);
		AbstractAccount a2 = cache.cloneAccount(AccountCache.SAVING);

		assertNotSame("clones must be different instances", a1, a2);

		// mutating one must not affect the other
		a1.deposit(100.0);
		assertEquals(100.0, a1.getBalance(), 0);
		assertEquals(0.0, a2.getBalance(), 0);
	}

	@Test
	public void testUnknownKeyThrows() {
		try {
			cache.cloneAccount("unknown");
			fail("Expected IllegalArgumentException for unknown key");
		} catch (IllegalArgumentException expected) {
			// ok
		} catch (CloneNotSupportedException e) {
			fail("Unexpected: " + e);
		}
	}
}

