package com.acomerci.offering.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.acomerci.offering.util.Util;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="born_date")
	private Date bornDate;

	@Column(name="doc_type")
	private String docType;

	private String document;

	private String email;

	@Column(name="last_name")
	private String lastName;

	private String name;
	
	private String password;

	@Column(name="phone_number")
	private String phoneNumber;

	private String pin;

	private int sex;

	private String username;
	
	private int active;

	//bi-directional many-to-one association to ActivityLog
	@OneToMany(mappedBy="user")
	private List<ActivityLog> activityLogs;

	//bi-directional many-to-one association to OfferingStatus
	@OneToMany(mappedBy="user")
	private List<OfferingStatus> offeringStatuses;

	//bi-directional many-to-one association to Bank
	@ManyToOne
	private Bank bank;
	
	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="users")
	@JsonBackReference
	private List<Role> roles;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBornDate() {
		return this.bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocument() {
		return this.document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@JsonIgnore
	public List<ActivityLog> getActivityLogs() {
		return this.activityLogs;
	}

	public void setActivityLogs(List<ActivityLog> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public ActivityLog addActivityLog(ActivityLog activityLog) {
		getActivityLogs().add(activityLog);
		activityLog.setUser(this);

		return activityLog;
	}

	public ActivityLog removeActivityLog(ActivityLog activityLog) {
		getActivityLogs().remove(activityLog);
		activityLog.setUser(null);

		return activityLog;
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
		offeringStatus.setUser(this);

		return offeringStatus;
	}

	public OfferingStatus removeOfferingStatus(OfferingStatus offeringStatus) {
		getOfferingStatuses().remove(offeringStatus);
		offeringStatus.setUser(null);

		return offeringStatus;
	}

	public Bank getBank() {
		return this.bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return (active == 1);
	}

	@Override
	public String toString() {
		return "[id=" + id + ", active=" + active + ", bornDate=" + (bornDate != null ? Util.sdfYMD.format(bornDate) : null) + ", docType="
				+ docType + ", document=" + document + ", email=" + email + ", lastName=" + lastName + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", sex=" + sex + ", bank=" + bank + "]";
	}
}