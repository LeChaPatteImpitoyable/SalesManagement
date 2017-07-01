package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.ProductType;

public class ManageProductTypeBF {
	private static ManageProductTypeBF instance = null;
    private HashMap<Integer, ProductType> hashProductTypes = new HashMap<Integer, ProductType>();
	
	protected ManageProductTypeBF() {
        hashProductTypes = getHashProductType();
	}
    
    public static ManageProductTypeBF getInstance() {
		if (instance == null) {
			instance = new ManageProductTypeBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductType> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<ProductType> productTypes = new ArrayList<ProductType>();
		
		try {
			productTypes = em.createNamedQuery("ProductType.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
    	
		return productTypes;
    }

	public HashMap<Integer, ProductType> getHashProductType() {
		List<ProductType> productTypes = getAll();
		
		for (ProductType productType : productTypes) {
			hashProductTypes.put(productType.getId(), productType);
		}
		
		return hashProductTypes;
	}
	
	public ProductType getProductType(int id) {
		return hashProductTypes.get(id);
	}
}
