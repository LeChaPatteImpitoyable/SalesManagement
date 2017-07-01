package com.acomerci.offering.model.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the business_hour database table.
 * 
 */
@Embeddable
public class BusinessHourPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "bank_id", insertable = false, updatable = false)
	private int bankId;

	@Column(name = "business_hour_type_id", insertable = false, updatable = false)
	private int businessHourTypeId;

	public BusinessHourPK() {
	}

	public BusinessHourPK(int bankId, int businessHourTypeId) {
		this.bankId = bankId;
		this.businessHourTypeId = businessHourTypeId;
	}

	public int getBankId() {
		return this.bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getBusinessHourTypeId() {
		return this.businessHourTypeId;
	}

	public void setBusinessHourTypeId(int businessHourTypeId) {
		this.businessHourTypeId = businessHourTypeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BusinessHourPK)) {
			return false;
		}
		BusinessHourPK castOther = (BusinessHourPK) other;
		return (this.bankId == castOther.bankId) && (this.businessHourTypeId == castOther.businessHourTypeId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bankId;
		hash = hash * prime + this.businessHourTypeId;

		return hash;
	}
}