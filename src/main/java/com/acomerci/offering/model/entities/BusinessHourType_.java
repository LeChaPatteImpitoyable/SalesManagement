package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:51.046-0300")
@StaticMetamodel(BusinessHourType.class)
public class BusinessHourType_ {
	public static volatile SingularAttribute<BusinessHourType, Integer> id;
	public static volatile SingularAttribute<BusinessHourType, String> name;
	public static volatile ListAttribute<BusinessHourType, BusinessHour> businessHours;
}
