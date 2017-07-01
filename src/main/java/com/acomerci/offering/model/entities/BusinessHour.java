package com.acomerci.offering.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;

/**
 * The persistent class for the business_hour database table.
 * 
 */
@Entity
@Table(name = "business_hour")
@NamedQuery(name = "BusinessHour.findAll", query = "SELECT b FROM BusinessHour b")
public class BusinessHour implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BusinessHourPK id;

	private Time time;

	// bi-directional many-to-one association to Bank
	@ManyToOne
	private Bank bank;

	// bi-directional many-to-one association to BusinessHourType
	@ManyToOne
	@JoinColumn(name = "business_hour_type_id")
	private BusinessHourType businessHourType;

	public BusinessHour() {
	}

	public BusinessHourPK getId() {
		return this.id;
	}

	public void setId(BusinessHourPK id) {
		this.id = id;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BusinessHourType getBusinessHourType() {
		return this.businessHourType;
	}

	public void setBusinessHourType(BusinessHourType businessHourType) {
		this.businessHourType = businessHourType;
	}

	@Override
	public String toString() {
		return "[bank=" + bank + ", businessHourType=" + businessHourType + ", time=" + time + "]";
	}
}