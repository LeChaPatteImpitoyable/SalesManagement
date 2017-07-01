package com.acomerci.offering.model.entities;

import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:51.015-0300")
@StaticMetamodel(BusinessHour.class)
public class BusinessHour_ {
	public static volatile SingularAttribute<BusinessHour, BusinessHourPK> id;
	public static volatile SingularAttribute<BusinessHour, Time> time;
	public static volatile SingularAttribute<BusinessHour, Bank> bank;
	public static volatile SingularAttribute<BusinessHour, BusinessHourType> businessHourType;
}
