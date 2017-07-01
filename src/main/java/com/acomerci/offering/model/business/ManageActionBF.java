package com.acomerci.offering.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.acomerci.offering.model.entities.Action;

public class ManageActionBF {
	private static ManageActionBF instance = null;
    private HashMap<Integer, Action> hashActions = new HashMap<Integer, Action>();
    
    public static final int ACTION_LOGIN = 1;
    
    public static final int ACTION_CREATE_OFFERING = 2;
    public static final int ACTION_DUPLICATE_OFFERING = 3;
    public static final int ACTION_EDIT_OFFERING = 4;
    public static final int ACTION_REMOVE_OFFERING = 5;
    public static final int ACTION_PUBLISH_OFFERING = 6;
    public static final int ACTION_RETIRE_OFFERING = 7;
    
    public static final int ACTION_VIEW_USERS = 8;
    public static final int ACTION_CREATE_USER = 9;
    public static final int ACTION_EDIT_USER = 10;
    public static final int ACTION_DELETE_USER = 11;
    
    public static final int ACTION_VIEW_ROLES = 12;
    public static final int ACTION_CREATE_ROLE = 13;
    public static final int ACTION_EDIT_ROLE = 14;
    public static final int ACTION_DELETE_ROLE = 15;
    
    public static final int ACTION_VIEW_CHANNEL_CONF = 16;
    public static final int ACTION_EDIT_CHANNEL_CONF = 17;

    public static final int ACTION_REQ_PUBLISH_OFFERING = 18;
    public static final int ACTION_REJECT_OFFERING = 19;
    public static final int ACTION_VIEW_OFFERING = 20;
    
    
    protected ManageActionBF() {
        hashActions = getActions();
	}
    
    public static ManageActionBF getInstance() {
		if (instance == null) {
			instance = new ManageActionBF();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Action> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<Action> actions = new ArrayList<Action>();
		
		try {
	    	actions = em.createNamedQuery("Action.findAll").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return actions;
    }

	public HashMap<Integer, Action> getActions() {
		List<Action> actions = getAll();
		
		for (Action action : actions) {
			hashActions.put(action.getId(), action);
		}
		
		return hashActions;
	}
	
	public Action getAction(int id) {
		return hashActions.get(id);
	}
}
