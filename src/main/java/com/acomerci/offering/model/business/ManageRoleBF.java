package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.security.core.context.SecurityContextHolder;

import com.acomerci.offering.model.entities.ActivityLog;
import com.acomerci.offering.model.entities.CurrentUser;
import com.acomerci.offering.model.entities.Role;
import com.acomerci.offering.model.entities.RoleConfig;
import com.acomerci.offering.model.entities.Role_;
import com.acomerci.offering.model.entities.User;
import com.acomerci.offering.model.entities.UserConfig;
import com.acomerci.offering.model.entities.User_;

public class ManageRoleBF {
	
	public static final int ROLE_INACTIVE = 0;
	public static final int ROLE_ACTIVE = 1;
    
	public ManageRoleBF() {
	}
	
	public Role find(int id) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			return em.find(Role.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
	
	public List<Role> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Role> roles = new ArrayList<Role>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Role> query = cb.createQuery(Role.class);
			Root<Role> r = query.from(Role.class);
			
			query.where(
				cb.and(
					cb.equal(r.get(Role_.bank.getName()), user.getBank()),
					cb.equal(r.get(User_.active.getName()), ROLE_ACTIVE)
				)
			);
			
			roles = em.createQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return roles;
    }
	
	public List<Role> getRoleNames() {
		EntityManager em = EMF.get().createEntityManager();
		List<Role> roles = new ArrayList<Role>();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Role> query = cb.createQuery(Role.class);
			Root<Role> r = query.from(Role.class);

			query.select(cb.construct(Role.class, r.get("id"), r.get("name")));
			query.where(
				cb.and(
					cb.equal(r.get(Role_.bank.getName()), user.getBank()),
					cb.equal(r.get(User_.active.getName()), ROLE_ACTIVE)
				)
			);
			
			roles = em.createQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return roles;
    }
	
	public Role findByName(String name) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	    	CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Role> query = cb.createQuery(Role.class);
			Root<Role> r = query.from(Role.class);
			
			query.where(
				cb.and(
						cb.equal(r.get(Role_.name.getName()), name),
						cb.equal(r.get(Role_.bank.getName()), user.getBank()),
						cb.equal(r.get(User_.active.getName()), ROLE_ACTIVE)
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
	
	public String create(RoleConfig roleIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			CurrentUser user = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (findByName(roleIn.getName().toUpperCase()) != null)
					return "Rol con nombre " + roleIn.getName().toUpperCase() + " existente.";
			
			em.getTransaction().begin();
			
			Role newRole = new Role();
			
			newRole.setActive(ROLE_ACTIVE);
			newRole.setBank(user.getBank());
			newRole.setName(roleIn.getName().toUpperCase());
			newRole.setDescription(roleIn.getDescription());
			
			newRole.setActions(roleIn.getActions());
			
			List<User> roleUsers = new ArrayList<User>();
			for (UserConfig userConfig : roleIn.getUsers()) {
				roleUsers.add(em.find(User.class, userConfig.getId()));
			}
			newRole.setUsers(roleUsers);
			
			em.persist(newRole);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_CREATE_ROLE, newRole.toString()));
			
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
	
	public String update(RoleConfig roleIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			Role role = em.find(Role.class, roleIn.getId());
			
			if(!role.getName().equalsIgnoreCase(roleIn.getName())) {
				if (findByName(roleIn.getName().toUpperCase()) != null)
					return "Rol con nombre " + roleIn.getName().toUpperCase() + " existente.";
			}
			
			em.getTransaction().begin();
			
			role.setName(roleIn.getName().toUpperCase());
			role.setDescription(roleIn.getDescription());
			
			role.setActions(roleIn.getActions());
			
			List<User> roleUsers = new ArrayList<User>();
			for (UserConfig userConfig : roleIn.getUsers()) {
				roleUsers.add(em.find(User.class, userConfig.getId()));
			}
			role.setUsers(roleUsers);
			
			em.merge(role);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_EDIT_ROLE, role.toString()));
			
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
	
	public boolean delete(RoleConfig roleIn) {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			Role role = em.getReference(Role.class, roleIn.getId());
			
			em.remove(role);
			
			em.persist(new ActivityLog(ManageActionBF.ACTION_DELETE_ROLE, role.toString()));
			
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
}
