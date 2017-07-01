package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.Date;

public class OfferingMainFields implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date creationDate;
	private Date publishDate;
	private Date dueDate;
	private Status status;

	public OfferingMainFields() {
	}
	
	public OfferingMainFields(Offering offering) {
		this.id = offering.getId();
		this.creationDate = offering.getCreationDate();
		this.publishDate = offering.getPublishDate();
		this.dueDate = offering.getDueDate();
		this.status = offering.getStatus();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}