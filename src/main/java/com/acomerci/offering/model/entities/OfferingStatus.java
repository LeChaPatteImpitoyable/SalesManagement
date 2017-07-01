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


/**
 * The persistent class for the offering_status database table.
 * 
 */
@Entity
@Table(name="offering_status")
@NamedQuery(name="OfferingStatus.findAll", query="SELECT o FROM OfferingStatus o")
public class OfferingStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp timestamp;
	
	private String comment;

	//bi-directional many-to-one association to Offering
	@ManyToOne
	private Offering offering;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public OfferingStatus() {
	}
	
	public OfferingStatus(Offering offering) {
		this.offering = offering;
		this.status = offering.getStatus();
		this.user = ((CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		this.timestamp = new Timestamp((new Date()).getTime());
	}
	
	public OfferingStatus(Offering offering, String comment) {
		this.offering = offering;
		this.status = offering.getStatus();
		this.timestamp = new Timestamp((new Date()).getTime());
		this.comment = comment;
		
		if (!comment.equals("batch")) {
			this.user = ((CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		}
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Offering getOffering() {
		return this.offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}