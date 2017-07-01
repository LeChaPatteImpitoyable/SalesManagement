package com.acomerci.offering.model.entities;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private List<String> actions;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getAuthorities());
        this.user = user;
        this.actions = setActions();
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return user.getId();
    }

    public Bank getBank() {
        return user.getBank();
    }
    
    public List<String> setActions() {
		List<String> actions = new ArrayList<String>();
		
		for (Role role : user.getRoles()) {
			for (Action action : role.getActions()) {
				actions.add(action.getName());
			}
		}
		
		return actions;
	}

	public List<String> getActions() {
		return actions;
	}

	public boolean hasPermission(Object permission) {
		return actions.contains((String) permission);
	}
	
	public boolean isEnabled() {
		return user.isEnabled();
	}
}