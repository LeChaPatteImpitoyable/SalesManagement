package com.acomerci.offering.model.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the business_day database table.
 * 
 */
@Embeddable
public class BusinessDayPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "bank_id", insertable = false, updatable = false)
	private int bankId;

	@Column(name = "day_id", insertable = false, updatable = false)
	private int dayId;

	public BusinessDayPK() {
	}

	public BusinessDayPK(int bankId, int dayId) {
		this.bankId = bankId;
		this.dayId = dayId;
	}

	public int getBankId() {
		return this.bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getDayId() {
		return this.dayId;
	}

	public void setDayId(int dayId) {
		this.dayId = dayId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BusinessDayPK)) {
			return false;
		}
		BusinessDayPK castOther = (BusinessDayPK) other;
		return (this.bankId == castOther.bankId) && (this.dayId == castOther.dayId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bankId;
		hash = hash * prime + this.dayId;

		return hash;
	}
}