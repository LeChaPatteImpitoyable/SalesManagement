package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the status database table.
 * 
 */
@Entity
@Table(name = "status")
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Offering
	@OneToMany(mappedBy="status")
	private List<Offering> offerings;

	//bi-directional many-to-one association to OfferingStatus
	@OneToMany(mappedBy="status")
	private List<OfferingStatus> offeringStatuses;

	public Status() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public List<Offering> getOfferings() {
		return this.offerings;
	}

	public void setOfferings(List<Offering> offerings) {
		this.offerings = offerings;
	}

	public Offering addOffering(Offering offering) {
		getOfferings().add(offering);
		offering.setStatus(this);

		return offering;
	}

	public Offering removeOffering(Offering offering) {
		getOfferings().remove(offering);
		offering.setStatus(null);

		return offering;
	}
	
	@JsonIgnore
	public List<OfferingStatus> getOfferingStatuses() {
		return this.offeringStatuses;
	}

	public void setOfferingStatuses(List<OfferingStatus> offeringStatuses) {
		this.offeringStatuses = offeringStatuses;
	}

	public OfferingStatus addOfferingStatus(OfferingStatus offeringStatus) {
		getOfferingStatuses().add(offeringStatus);
		offeringStatus.setStatus(this);

		return offeringStatus;
	}

	public OfferingStatus removeOfferingStatus(OfferingStatus offeringStatus) {
		getOfferingStatuses().remove(offeringStatus);
		offeringStatus.setStatus(null);

		return offeringStatus;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", name=" + name + "]";
	}
}