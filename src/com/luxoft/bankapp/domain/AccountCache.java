package com.luxoft.bankapp.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple cache holding prototype {@link AbstractAccount} instances
 * (one {@link SavingAccount}, one {@link CheckingAccount}, both with
 * all values set to 0).
 *
 * <p>When a client asks for a new account, it is cloned from the prototype
 * stored in the cache (Prototype design pattern).</p>
 */
public class AccountCache {

	public static final String SAVING = "saving";
	public static final String CHECKING = "checking";

	private final Map<String, AbstractAccount> cache = new HashMap<>();

	/** Loads the cache with one saving and one checking prototype, all values on 0. */
	public void load() {
		cache.put(SAVING, new SavingAccount(0, 0.0));
		cache.put(CHECKING, new CheckingAccount(0, 0.0, 0.0));
	}

	/**
	 * Returns a clone of the prototype stored under the given key.
	 *
	 * @param key {@link #SAVING} or {@link #CHECKING}
	 * @return a freshly cloned {@link AbstractAccount}
	 * @throws CloneNotSupportedException if cloning fails
	 * @throws IllegalArgumentException   if there is no prototype for the key
	 */
	public AbstractAccount cloneAccount(String key) throws CloneNotSupportedException {
		AbstractAccount prototype = cache.get(key);
		if (prototype == null) {
			throw new IllegalArgumentException("No prototype account for key: " + key);
		}
		return prototype.clone();
	}
}

