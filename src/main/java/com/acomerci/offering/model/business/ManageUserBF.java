package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.acomerci.offering.model.entities.ActivityLog;
import com.acomerci.offering.model.entities.CurrentUser;
import com.acomerci.offering.model.entities.User;
import com.acomerci.offering.model.entities.UserConfig;
import com.acomerci.offering.model.entities.User_;

public class ManageUserBF implements UserDetailsService {
	
	public static final int USER_INACTIVE = 0;
	public static final int USER_ACTIVE = 1;

	public ManageUserBF() {
	}
	
	public List<User> getAll(int showActive) {
		EntityManager em = EMF.get().createEntityManager();
		
		List<User> users = new ArrayList<User>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (user != null) {
		    	CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<User> query = cb.createQuery(User.class);
				Root<User> o = query.from(User.class);
				
				query.where(
					cb.and(
						cb.equal(o.get(User_.bank.getName()), user.getBank()),
						cb.or(
							cb.equal(o.get(User_.active.getName()), USER_ACTIVE),
						 	cb.equal(o.get(User_.active.getName()), showActive)
						)
					)
				);
				
				users = em.createQuery(query).getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return users;
    }
	
	public User findByEmail(String email) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> query = cb.createQuery(User.class);
			Root<User> u = query.from(User.class);
			
			query.where(
				cb.and(
					cb.equal(u.get(User_.email.getName()), email),
					cb.equal(u.get(User_.bank.getName()), currentUser.getBank()),
					cb.equal(u.get(User_.active.getName()), USER_ACTIVE)
				)
			);
			
			return em.createQuery(query).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
    }

	public String create(UserConfig userIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (findByEmail(userIn.getEmail()) != null)
				return "Usuario con email " + userIn.getEmail() + " existente.";
			
			em.getTransaction().begin();
			
			User newUser = new User();
			
			newUser.setEmail(userIn.getEmail());
			newUser.setLastName(userIn.getLastName());
			newUser.setName(userIn.getName());
			newUser.setBornDate(userIn.getBornDate());
			newUser.setSex(userIn.getSex());
			newUser.setDocType(userIn.getDocType());
			newUser.setDocument(userIn.getDocument());
			newUser.setPhoneNumber(userIn.getPhoneNumber());
			
			newUser.setPassword((userIn.getName().toLowerCase().charAt(0) + userIn.getLastName().toLowerCase()).replaceAll("\\s",""));
			
			newUser.setActive(USER_ACTIVE);
			newUser.setBank(currentUser.getBank());
			
			em.persist(newUser);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_CREATE_USER, newUser.toString()));
			
			em.getTransaction().commit();
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return "error";
	}
	
	public String update(UserConfig userIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			User user = em.find(User.class, userIn.getId());
			
			if(!user.getEmail().equalsIgnoreCase(userIn.getEmail())) {
				if (findByEmail(userIn.getEmail()) != null)
					return "Usuario con email " + userIn.getEmail() + " existente.";
			}
			
			em.getTransaction().begin();
			user.setEmail(userIn.getEmail());
			user.setLastName(userIn.getLastName());
			user.setName(userIn.getName());
			user.setBornDate(userIn.getBornDate());
			user.setSex(userIn.getSex());
			user.setDocType(userIn.getDocType());
			user.setDocument(userIn.getDocument());
			user.setPhoneNumber(userIn.getPhoneNumber());
			
			user.setActive(USER_ACTIVE);
			
			em.merge(user);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_EDIT_USER, user.toString()));
			
			em.getTransaction().commit();
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return "error";
	}
	
	public boolean delete(UserConfig userIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			User user = em.find(User.class, userIn.getId());
			
			user.setActive(USER_INACTIVE);
			
			em.merge(user);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_DELETE_USER, user));
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return false;
	}
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		EntityManager em = EMF.create().createEntityManager();
		
		try {
			em.clear();
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> query = cb.createQuery(User.class);
			Root<User> usu = query.from(User.class);
			query.where(cb.equal(usu.get("email"), email));
			User user = em.createQuery(query).getSingleResult();
			
			em.refresh(user);
			
			em.getTransaction().begin();
			em.persist(new ActivityLog(ManageActionBF.ACTION_LOGIN, user));
			em.getTransaction().commit();
	        
			return new CurrentUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return null;
    }
	
	public String updateSelf(UserConfig userIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			User user = em.find(User.class, currentUser.getId());
			
			if(!user.getPassword().equals(userIn.getCheckPassword()))
				return "Contrase&ntilde;a incorrecta.";
			
			em.getTransaction().begin();
			user.setPassword(userIn.getNewPassword());
			
			em.merge(user);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_EDIT_USER, "Modificar password"));
			
			em.getTransaction().commit();
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return "error";
	}
}
