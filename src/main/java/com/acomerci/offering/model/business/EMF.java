package com.acomerci.offering.model.business;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory create() {
		emf = Persistence.createEntityManagerFactory("SalesManagement");

		return emf;
	}

	public static EntityManagerFactory get() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("SalesManagement");
		}

		return emf;
	}
}
