package com.acomerci.offering.model.business.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.scheduling.annotation.Scheduled;

import com.acomerci.offering.model.business.EMF;
import com.acomerci.offering.model.business.ManageOfferingBF;
import com.acomerci.offering.model.business.ManageStatusBF;
import com.acomerci.offering.model.entities.Offering;
import com.acomerci.offering.util.Util;

public class StatusChangeTask {
    private ManageOfferingBF offeringBF = new ManageOfferingBF();
    
	@Scheduled(cron="0 0 23 * * ?")
	public void cambiarEstadoOfertas() {
	    System.out.println("StatusChangeTask: " + Util.sdfYMD.format(new Date()));
	    
	    EntityManager em = EMF.get().createEntityManager();
	    
	    try {
	    	Calendar yesterday = Calendar.getInstance();
			yesterday.set(Calendar.HOUR_OF_DAY, 0);
			yesterday.set(Calendar.MINUTE, 0);
			yesterday.set(Calendar.SECOND, 0);
			yesterday.set(Calendar.MILLISECOND, 0);
			yesterday.add(Calendar.DAY_OF_MONTH, -1);
		    
		    CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Offering> query = cb.createQuery(Offering.class);
		    Root<Offering> of = query.from(Offering.class);
		    query.where(
		    	cb.and(
		    		cb.equal(of.get("status"), ManageStatusBF.getInstance().getStatus(ManageStatusBF.STATUS_PUBLISHED)),
		    		cb.equal(of.get("dueDate"), yesterday.getTime())
		    	)
		    );
		    
		    List<Offering> rsOfferings = em.createQuery(query).getResultList();
		    
		    for (Offering offering : rsOfferings) {
				offeringBF.expire(offering);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
	}
	
}
