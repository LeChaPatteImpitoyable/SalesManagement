package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Entidad auxiliar para abm de roles
 * 
 */
public class RoleConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String description;
	private List<Action> actions;
	private List<UserConfig> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<UserConfig> getUsers() {
		return users;
	}

	public void setUsers(List<UserConfig> users) {
		this.users = users;
	}
}