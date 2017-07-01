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
 * The persistent class for the bank database table.
 * 
 */
@Entity
@Table(name = "bank")
@NamedQuery(name="Bank.findAll", query="SELECT b FROM Bank b")
public class Bank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to BusinessDay
	@OneToMany(mappedBy="bank")
	private List<BusinessDay> businessDays;

	//bi-directional many-to-one association to BusinessHour
	@OneToMany(mappedBy="bank")
	private List<BusinessHour> businessHours;

	//bi-directional many-to-one association to Offering
	@OneToMany(mappedBy="bank")
	private List<Offering> offerings;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="bank")
	private List<User> users;
	
	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="bank")
	private List<Role> roles;

	public Bank() {
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
		businessDay.setBank(this);

		return businessDay;
	}

	public BusinessDay removeBusinessDay(BusinessDay businessDay) {
		getBusinessDays().remove(businessDay);
		businessDay.setBank(null);

		return businessDay;
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
		businessHour.setBank(this);

		return businessHour;
	}

	public BusinessHour removeBusinessHour(BusinessHour businessHour) {
		getBusinessHours().remove(businessHour);
		businessHour.setBank(null);

		return businessHour;
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
		offering.setBank(this);

		return offering;
	}

	public Offering removeOffering(Offering offering) {
		getOfferings().remove(offering);
		offering.setBank(null);

		return offering;
	}
	
	@JsonIgnore
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setBank(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setBank(null);

		return user;
	}
	
	@JsonIgnore
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role addRole(Role role) {
		getRoles().add(role);
		role.setBank(this);

		return role;
	}

	public Role removeRole(Role role) {
		getRoles().remove(role);
		role.setBank(null);

		return role;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + "]";
	}
}