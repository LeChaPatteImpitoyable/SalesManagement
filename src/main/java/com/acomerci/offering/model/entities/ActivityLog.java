package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.core.context.SecurityContextHolder;

import com.acomerci.offering.model.business.ManageActionBF;


/**
 * The persistent class for the activity_log database table.
 * 
 */
@Entity
@Table(name="activity_log")
@NamedQuery(name="ActivityLog.findAll", query="SELECT a FROM ActivityLog a")
public class ActivityLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String message;

	private Timestamp timestamp;

	//bi-directional many-to-one association to Action
	@ManyToOne
	private Action action;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public ActivityLog() {
	}
	
	public ActivityLog(int actionId) {
		this.timestamp = new Timestamp((new Date()).getTime());
		this.user = ((CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		this.action = ManageActionBF.getInstance().getAction(actionId);
	}
	
	public ActivityLog(int actionId, User user) {
		this.timestamp = new Timestamp((new Date()).getTime());
		this.user = user;
		this.action = ManageActionBF.getInstance().getAction(actionId);
	}
	
	public ActivityLog(int actionId, String message) {
		this.timestamp = new Timestamp((new Date()).getTime());
		this.user = ((CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		this.action = ManageActionBF.getInstance().getAction(actionId);
		this.message = message;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Action getAction() {
		return this.action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}