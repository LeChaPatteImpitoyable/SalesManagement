package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the day database table.
 * 
 */
@Entity
@Table(name = "day")
@NamedQuery(name="Day.findAll", query="SELECT d FROM Day d")
public class Day implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to BusinessDay
	@OneToMany(mappedBy="day")
	private List<BusinessDay> businessDays;

	public Day() {
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
	public List<BusinessDay> getBusinessDays() {
		return this.businessDays;
	}

	public void setBusinessDays(List<BusinessDay> businessDays) {
		this.businessDays = businessDays;
	}

	public BusinessDay addBusinessDay(BusinessDay businessDay) {
		getBusinessDays().add(businessDay);
		businessDay.setDay(this);

		return businessDay;
	}

	public BusinessDay removeBusinessDay(BusinessDay businessDay) {
		getBusinessDays().remove(businessDay);
		businessDay.setDay(null);

		return businessDay;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + "]";
	}
}