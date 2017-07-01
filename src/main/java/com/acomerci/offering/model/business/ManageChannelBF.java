package com.acomerci.offering.model.business;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.security.core.context.SecurityContextHolder;

import com.acomerci.offering.model.entities.ActivityLog;
import com.acomerci.offering.model.entities.BusinessDay;
import com.acomerci.offering.model.entities.BusinessDayPK;
import com.acomerci.offering.model.entities.BusinessHour;
import com.acomerci.offering.model.entities.BusinessHourPK;
import com.acomerci.offering.model.entities.ChannelConfig;
import com.acomerci.offering.model.entities.CurrentUser;

public class ManageChannelBF {
	public ManageChannelBF() {
	}
	
	public ChannelConfig get() {
		ChannelConfig channel = new ChannelConfig();
		
		try {
	    	channel.setOpeningTime(getBusinessHour(ManageBusinessHourTypeBF.OPENING_TIME));
	    	channel.setClosingTime(getBusinessHour(ManageBusinessHourTypeBF.CLOSING_TIME));
	    	channel.setBusinessDays(getBusinessDays());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return channel;
    }
	
	private BusinessHour getBusinessHour(int businessHourId) {
		EntityManager em = EMF.get().createEntityManager();
		BusinessHour businessHour = new BusinessHour();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			BusinessHourPK businessHourPK = new BusinessHourPK(user.getBank().getId(), businessHourId);
			
			businessHour = em.find(BusinessHour.class, businessHourPK);
			
			if (businessHour == null) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				
				businessHour = new BusinessHour();
				
				businessHour.setId(businessHourPK);
				businessHour.setBank(user.getBank());
				businessHour.setBusinessHourType(ManageBusinessHourTypeBF.getInstance().getBusinessHourType(businessHourId));
				businessHour.setTime(new Time(cal.getTime().getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return businessHour;
	}
	
	private List<BusinessDay> getBusinessDays() {
		EntityManager em = EMF.get().createEntityManager();
		List<BusinessDay> businessDays = new ArrayList<BusinessDay>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<BusinessDay> query = cb.createQuery(BusinessDay.class);
			
			Root<BusinessDay> businessDay = query.from(BusinessDay.class);
			
			query.where(cb.equal(businessDay.get("bank"), user.getBank()));
			
			businessDays = em.createQuery(query).getResultList();
			
			if (businessDays.isEmpty()) {
				for (int dayId = 1; dayId < 8; dayId++) {
					BusinessDay bd = new BusinessDay();
					bd.setId(new BusinessDayPK(user.getBank().getId(), dayId));
					bd.setBank(user.getBank());
					bd.setDay(ManageDayBF.getInstance().getDay(dayId));
					bd.setEnable((byte) 0);
					businessDays.add(bd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return businessDays;
	}
	
	public boolean update(ChannelConfig configuration) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			em.merge(configuration.getOpeningTime());
			em.merge(configuration.getClosingTime());
			
			for (BusinessDay businessDay : configuration.getBusinessDays()) {
				em.merge(businessDay);
			}
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_EDIT_CHANNEL_CONF, configuration.toString()));
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}
}
