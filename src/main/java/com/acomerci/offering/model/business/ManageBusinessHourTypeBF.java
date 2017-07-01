package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.BusinessHourType;

public class ManageBusinessHourTypeBF {
	private static ManageBusinessHourTypeBF instance = null;
    private HashMap<Integer, BusinessHourType> hashBusinessHourTypes = new HashMap<Integer, BusinessHourType>();
    
    public static final int OPENING_TIME = 1;
    public static final int CLOSING_TIME = 2;
	
	protected ManageBusinessHourTypeBF() {
        hashBusinessHourTypes = getHashBusinessHourType();
	}
    
    public static ManageBusinessHourTypeBF getInstance() {
		if (instance == null) {
			instance = new ManageBusinessHourTypeBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<BusinessHourType> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<BusinessHourType> businessHourTypes = new ArrayList<BusinessHourType>();
		
		try {
	    	businessHourTypes = em.createNamedQuery("BusinessHourType.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return businessHourTypes;
    }

	public HashMap<Integer, BusinessHourType> getHashBusinessHourType() {
		List<BusinessHourType> businessHourTypes = getAll();
		
		for (BusinessHourType businessHourType : businessHourTypes) {
			hashBusinessHourTypes.put(businessHourType.getId(), businessHourType);
		}
		
		return hashBusinessHourTypes;
	}
	
	public BusinessHourType getBusinessHourType(int id) {
		return hashBusinessHourTypes.get(id);
	}
}
