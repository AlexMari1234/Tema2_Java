package com.luxoft.bankapp.utils;

import java.io.Serializable;

import com.luxoft.bankapp.domain.Client;

public interface ClientRegistrationListener extends Serializable {
	void onClientAdded(Client client);
}
