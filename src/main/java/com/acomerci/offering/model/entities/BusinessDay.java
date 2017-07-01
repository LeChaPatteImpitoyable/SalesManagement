package com.acomerci.offering.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the business_day database table.
 * 
 */
@Entity
@Table(name="business_day")
@NamedQuery(name="BusinessDay.findAll", query="SELECT b FROM BusinessDay b")
public class BusinessDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BusinessDayPK id;

	private byte enable;

	//bi-directional many-to-one association to Bank
	@ManyToOne
	private Bank bank;

	//bi-directional many-to-one association to Day
	@ManyToOne
	private Day day;

	public BusinessDay() {
	}

	public BusinessDayPK getId() {
		return this.id;
	}

	public void setId(BusinessDayPK id) {
		this.id = id;
	}

	public byte getEnable() {
		return this.enable;
	}

	public void setEnable(byte enable) {
		this.enable = enable;
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Day getDay() {
		return this.day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "[bank=" + bank + ", day=" + day + ", enable=" + enable + "]";
	}
}