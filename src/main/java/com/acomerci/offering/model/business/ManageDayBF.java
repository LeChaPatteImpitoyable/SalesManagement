package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.Day;

public class ManageDayBF {
	private static ManageDayBF instance = null;
    private HashMap<Integer, Day> hashDays = new HashMap<Integer, Day>();
	
	protected ManageDayBF() {
        hashDays = getHashDay();
	}
    
    public static ManageDayBF getInstance() {
		if (instance == null) {
			instance = new ManageDayBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Day> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Day> days = new ArrayList<Day>();
		
		try {
			days = em.createNamedQuery("Day.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
    	
		return days;
    }

	public HashMap<Integer, Day> getHashDay() {
		List<Day> days = getAll();
		
		for (Day day : days) {
			hashDays.put(day.getId(), day);
		}
		
		return hashDays;
	}
	
	public Day getDay(int id) {
		return hashDays.get(id);
	}
}
