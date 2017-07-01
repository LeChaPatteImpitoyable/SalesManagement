package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.security.core.context.SecurityContextHolder;

import com.acomerci.offering.model.entities.CurrentUser;
import com.acomerci.offering.model.entities.Offering;
import com.acomerci.offering.model.entities.Offering_;
import com.acomerci.offering.model.entities.Product;
import com.acomerci.offering.model.entities.Product_;

public class ManageProductBF {
	private static ManageProductBF instance = null;
    private HashMap<Integer, Product> hashProducts = new HashMap<Integer, Product>();
	
	protected ManageProductBF() {
        hashProducts = getHashProduct();
	}
    
    public static ManageProductBF getInstance() {
		if (instance == null) {
			instance = new ManageProductBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Product> products = new ArrayList<Product>();
		
		try {
			products = em.createNamedQuery("Product.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
    	
		return products;
    }
	
	public List<Product> getBankProductNames() {
		EntityManager em = EMF.get().createEntityManager();
		List<Product> products = new ArrayList<Product>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<String> query = cb.createQuery(String.class);
			Root<Product> p = query.from(Product.class);
			Join<Product, Offering> o = p.join(Product_.offering);
			
			query.select(p.get(Product_.name)).distinct(true);
			query.where(cb.equal(o.get(Offering_.bank.getName()), user.getBank()));
			
			List<String> result = em.createQuery(query).getResultList();
			
			for (String name : result) {
				Product prod = new Product();
				prod.setName(name);
				products.add(prod);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return products;
    }

	public HashMap<Integer, Product> getHashProduct() {
		List<Product> products = getAll();
		
		for (Product product : products) {
			hashProducts.put(product.getId(), product);
		}
		
		return hashProducts;
	}
	
	public Product getProduct(int id) {
		return hashProducts.get(id);
	}
}
