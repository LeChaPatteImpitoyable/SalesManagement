package com.acomerci.offering.model.entities;

import java.util.List;

public class ChannelConfig {
	private BusinessHour openingTime;

	private BusinessHour closingTime;

	private List<BusinessDay> businessDays;

	public ChannelConfig() {
	}

	public BusinessHour getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(BusinessHour openingTime) {
		this.openingTime = openingTime;
	}

	public BusinessHour getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(BusinessHour closingTime) {
		this.closingTime = closingTime;
	}
	
	public List<BusinessDay> getBusinessDays() {
		return businessDays;
	}

	public void setBusinessDays(List<BusinessDay> businessDays) {
		this.businessDays = businessDays;
	}

	@Override
	public String toString() {
		return "[openingTime=" + openingTime + ", closingTime=" + closingTime + ", businessDays=" + businessDays + "]";
	}
}
