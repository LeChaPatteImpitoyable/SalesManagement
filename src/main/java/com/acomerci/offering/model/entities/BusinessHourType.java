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
 * The persistent class for the business_hour_type database table.
 * 
 */
@Entity
@Table(name="business_hour_type")
@NamedQuery(name="BusinessHourType.findAll", query="SELECT b FROM BusinessHourType b")
public class BusinessHourType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to BusinessHour
	@OneToMany(mappedBy="businessHourType")
	private List<BusinessHour> businessHours;

	public BusinessHourType() {
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
	public List<BusinessHour> getBusinessHours() {
		return this.businessHours;
	}

	public void setBusinessHours(List<BusinessHour> businessHours) {
		this.businessHours = businessHours;
	}

	public BusinessHour addBusinessHour(BusinessHour businessHour) {
		getBusinessHours().add(businessHour);
		businessHour.setBusinessHourType(this);

		return businessHour;
	}

	public BusinessHour removeBusinessHour(BusinessHour businessHour) {
		getBusinessHours().remove(businessHour);
		businessHour.setBusinessHourType(null);

		return businessHour;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + "]";
	}
}