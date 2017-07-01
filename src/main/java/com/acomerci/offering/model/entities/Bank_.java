package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:50.968-0300")
@StaticMetamodel(Bank.class)
public class Bank_ {
	public static volatile SingularAttribute<Bank, Integer> id;
	public static volatile SingularAttribute<Bank, String> name;
	public static volatile ListAttribute<Bank, BusinessDay> businessDays;
	public static volatile ListAttribute<Bank, BusinessHour> businessHours;
	public static volatile ListAttribute<Bank, Offering> offerings;
	public static volatile ListAttribute<Bank, User> users;
	public static volatile ListAttribute<Bank, Role> roles;
}
