package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:50.984-0300")
@StaticMetamodel(BusinessDay.class)
public class BusinessDay_ {
	public static volatile SingularAttribute<BusinessDay, BusinessDayPK> id;
	public static volatile SingularAttribute<BusinessDay, Byte> enable;
	public static volatile SingularAttribute<BusinessDay, Bank> bank;
	public static volatile SingularAttribute<BusinessDay, Day> day;
}
