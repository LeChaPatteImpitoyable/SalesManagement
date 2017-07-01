package com.acomerci.offering.session;

import java.util.Date;

import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.factories.SessionCustomizer;

public class DbSessionCustomizer implements SessionCustomizer {

	@Override
	public void customize(Session session) throws Exception {
		System.out.println("DbSessionCustomizer.customize: " + new Date());
		DatabaseLogin login = (DatabaseLogin)session.getDatasourceLogin();
        login.setConnectionHealthValidatedOnError(false);
	}

}
