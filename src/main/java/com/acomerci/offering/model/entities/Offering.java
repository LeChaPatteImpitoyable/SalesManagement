package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.acomerci.offering.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the offering database table.
 * 
 */
@Entity
@Table(name = "offering")
@NamedQuery(name = "Offering.findAll", query = "SELECT o FROM Offering o")
public class Offering implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "creation_date")
	private Date creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "due_date")
	private Date dueDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "publish_date")
	private Date publishDate;

	// bi-directional many-to-one association to Bank
	@ManyToOne
	private Bank bank;

	// bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "offering", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> products;

	// bi-directional many-to-one association to OfferingStatus
	@OneToMany(mappedBy = "offering")
	private List<OfferingStatus> offeringStatuses;

	public Offering() {
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

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setOffering(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setOffering(null);

		return product;
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
		offeringStatus.setOffering(this);

		return offeringStatus;
	}

	public OfferingStatus removeOfferingStatus(OfferingStatus offeringStatus) {
		getOfferingStatuses().remove(offeringStatus);
		offeringStatus.setOffering(null);

		return offeringStatus;
	}

	@Override
	public String toString() {
		return "Offering [id=" + id + ", creationDate=" + Util.sdfYMD.format(creationDate) + ", dueDate=" + Util.sdfYMD.format(dueDate)
				+ ", publishDate=" + publishDate + ", bank=" + bank + ", status=" + status + "]";
	}
}