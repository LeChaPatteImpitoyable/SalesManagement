package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.security.core.context.SecurityContextHolder;

import com.acomerci.offering.model.entities.ActivityLog;
import com.acomerci.offering.model.entities.CurrentUser;
import com.acomerci.offering.model.entities.FinancialService;
import com.acomerci.offering.model.entities.Offering;
import com.acomerci.offering.model.entities.OfferingStatus;
import com.acomerci.offering.model.entities.OfferingStatus_;
import com.acomerci.offering.model.entities.Offering_;
import com.acomerci.offering.model.entities.Product;
import com.acomerci.offering.model.entities.RejectOffering;

public class ManageOfferingBF {
    private ManageStatusBF statusBF = new ManageStatusBF();
    
	public ManageOfferingBF() {
	}
	
	public List<Offering> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Offering> offerings = new ArrayList<Offering>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Offering> query = cb.createQuery(Offering.class);
			Root<Offering> o = query.from(Offering.class);
			
			query.where(cb.equal(o.get(Offering_.bank.getName()), user.getBank()));
			query.orderBy(cb.desc(o.get(Offering_.creationDate.getName())));
			
			offerings = em.createQuery(query).getResultList();
			
			return offerings;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return offerings;
    }
	
	public Offering find(int id) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			Offering offering = em.find(Offering.class, id);
			return offering;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return null;
	}
	
	public OfferingStatus getLastOfferingStatus(int offeringId) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
			Root<OfferingStatus> o = query.from(OfferingStatus.class);

			query.select(cb.max(o.get(OfferingStatus_.id)));
			query.where(cb.equal(o.get(OfferingStatus_.offering.getName()), em.find(Offering.class, offeringId)));

			Integer idLastOfferingStatus = em.createQuery(query).getSingleResult();
			
			return em.find(OfferingStatus.class, idLastOfferingStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
	
	public boolean create(Offering offeringIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Offering offering = new Offering();
			offering.setBank(user.getBank());
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_SAVED));
			offering.setCreationDate(offeringIn.getCreationDate());
			offering.setPublishDate(offeringIn.getPublishDate());
			offering.setDueDate(offeringIn.getDueDate());
			offering.setProducts(offeringIn.getProducts());
			
			addOfferingToProducts(offering);
			
			em.persist(offering);
			
			em.persist(new OfferingStatus(offering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_CREATE_OFFERING, offering.toString()));
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}
	
	public boolean update(Offering offering, List<Product> productsToDelete, List<FinancialService> financialServicesToDelete) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			addOfferingToProducts(offering);
			
			for (Product product : productsToDelete) {
				product = em.getReference(Product.class, product.getId());
				em.remove(product);
			}
			
			for (FinancialService financialService : financialServicesToDelete) {
				financialService = em.getReference(FinancialService.class, financialService.getId());
				em.remove(financialService);
			}
			
			if (offering.getStatus().getId() == ManageStatusBF.getInstance().getStatus(ManageStatusBF.STATUS_REJECTED).getId())
				offering.setStatus(ManageStatusBF.getInstance().getStatus(ManageStatusBF.STATUS_SAVED));
			
			em.merge(offering);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_EDIT_OFFERING, offering.toString()));
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}
	
	public String requestPublish(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			offering = find(offering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_PENDING_PUBLISH));
			
			em.merge(offering);
			
			em.persist(new OfferingStatus(offering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_REQ_PUBLISH_OFFERING, String.valueOf(offering.getId())));
			
			em.getTransaction().commit();
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return "error";
		} finally {
			em.close();
		}
	}
	
	public void publish(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			offering = find(offering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_PUBLISHED));
			offering.setPublishDate(new Date());
			
			em.merge(offering);
			
			em.persist(new OfferingStatus(offering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_PUBLISH_OFFERING, String.valueOf(offering.getId())));
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public String reject(RejectOffering rejectOffering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Offering offering = find(rejectOffering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_REJECTED));
			
			em.merge(offering);
			
			em.persist(new OfferingStatus(offering, rejectOffering.getRejectReason()));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_REJECT_OFFERING, String.valueOf(offering.getId())));
			
			em.getTransaction().commit();
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return "error";
		} finally {
			em.close();
		}
	}
	
	public void delete(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			offering = find(offering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_REMOVED));
			
			em.merge(offering);
			
			em.persist(new OfferingStatus(offering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_REMOVE_OFFERING, String.valueOf(offering.getId())));
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public void retire(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			offering = find(offering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_RETIRED));
			
			em.merge(offering);
			
			em.persist(new OfferingStatus(offering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_RETIRE_OFFERING, String.valueOf(offering.getId())));
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public void expire(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			offering = find(offering.getId());
			
			offering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_EXPIRED));
			
			em.persist(new OfferingStatus(offering, "batch"));
			
			em.merge(offering);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public void duplicate(Offering offering) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Offering newOffering = new Offering();

			newOffering.setBank(user.getBank());
			newOffering.setStatus(statusBF.getStatus(ManageStatusBF.STATUS_SAVED));
			newOffering.setCreationDate(new Date());
			newOffering.setDueDate(offering.getDueDate());
			newOffering.setProducts(duplicateProducts(offering, newOffering));
			
			em.persist(newOffering);
			
			em.persist(new OfferingStatus(newOffering));
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_DUPLICATE_OFFERING, offering.toString()));
			
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	private void addOfferingToProducts(Offering offering) {
		for (Product product : offering.getProducts()) {
			product.setOffering(offering);

			addProductToFinancialServices(product);
		}
	}
	
	private void addProductToFinancialServices(Product product) {
		for (FinancialService financialService : product.getFinancialServices()) {
			financialService.setProduct(product);
		}
	}
	
	private List<Product> duplicateProducts(Offering offering, Offering newOffering) {
		List<Product> newProducts = new ArrayList<Product>();
		
		for (Product product : offering.getProducts()) {
			Product newProduct = new Product();

			newProduct.setOffering(newOffering);
			newProduct.setProductType(product.getProductType());
			newProduct.setCurrency(product.getCurrency());
			newProduct.setName(product.getName());
			newProduct.setMaxAmount(product.getMaxAmount());
			newProduct.setMaxTerm(product.getMaxTerm());
			newProduct.setTeaRate(product.getTeaRate());
			newProduct.setTeaRateDelay(product.getTeaRateDelay());
			newProduct.setTeaRateClient(product.getTeaRateClient());
			newProduct.setTeaRateDelayClient(product.getTeaRateDelayClient());
			newProduct.setCommissionOpening(product.getCommissionOpening());
			newProduct.setCommissionEarlyCancellation(product.getCommissionEarlyCancellation());
			newProduct.setCommissionDownPayment(product.getCommissionDownPayment());
			newProduct.setImage(product.getImage());
			newProduct.setImageName(product.getImageName());
			
			newProduct.setFinancialServices(duplicateFinancialServices(product, newProduct));
			
			newProducts.add(newProduct);
		}
		
		return newProducts;
	}
	
	private List<FinancialService> duplicateFinancialServices(Product product, Product newProduct) {
		List<FinancialService> newFinancialServices = new ArrayList<FinancialService>();
		
		for (FinancialService financialService : product.getFinancialServices()) {
			FinancialService newFinancialService = new FinancialService();

			newFinancialService.setProduct(newProduct);
			newFinancialService.setName(financialService.getName());
			
			newFinancialServices.add(newFinancialService);
		}
		
		return newFinancialServices;
	}
}
