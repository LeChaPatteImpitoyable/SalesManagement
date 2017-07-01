package com.acomerci.offering.model.entities;

public class RejectOffering {
	private int id;
	
	private String rejectReason;

	public RejectOffering() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", rejectReason=" + rejectReason + "]";
	}
}
