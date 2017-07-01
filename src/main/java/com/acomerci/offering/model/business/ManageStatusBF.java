package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.Status;

public class ManageStatusBF {
	private static ManageStatusBF instance = null;
	private HashMap<Integer, Status> hashStatus = new HashMap<Integer, Status>();

	public static final int STATUS_SAVED = 1;
	public static final int STATUS_PUBLISHED = 2;
	public static final int STATUS_EXPIRED = 3;
	public static final int STATUS_REMOVED = 4;
	public static final int STATUS_RETIRED = 5;
	public static final int STATUS_PENDING_PUBLISH = 6;
	public static final int STATUS_REJECTED = 7;

	protected ManageStatusBF() {
		hashStatus = getHashStatus();
	}

	public static ManageStatusBF getInstance() {
		if (instance == null) {
			instance = new ManageStatusBF();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<Status> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Status> statusList = new ArrayList<Status>();
		
		try {
			statusList = em.createNamedQuery("Status.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return statusList;
	}

	public HashMap<Integer, Status> getHashStatus() {
		List<Status> statuses = getAll();

		for (Status status : statuses) {
			hashStatus.put(status.getId(), status);
		}

		return hashStatus;
	}

	public Status getStatus(int id) {
		return hashStatus.get(id);
	}
}
