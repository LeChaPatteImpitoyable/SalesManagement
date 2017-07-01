package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.Currency;

public class ManageCurrencyBF {
	private static ManageCurrencyBF instance = null;
    private HashMap<Integer, Currency> hashCurrencies = new HashMap<Integer, Currency>();
	
	protected ManageCurrencyBF() {
        hashCurrencies = getHashCurrency();
	}
    
    public static ManageCurrencyBF getInstance() {
		if (instance == null) {
			instance = new ManageCurrencyBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Currency> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Currency> currencies = new ArrayList<Currency>();
		
		try {
			currencies = em.createNamedQuery("Currency.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return currencies;
    }

	public HashMap<Integer, Currency> getHashCurrency() {
		List<Currency> currencies = getAll();
		
		for (Currency currency : currencies) {
			hashCurrencies.put(currency.getId(), currency);
		}
		
		return hashCurrencies;
	}
	
	public Currency getCurrency(int id) {
		return hashCurrencies.get(id);
	}
}
