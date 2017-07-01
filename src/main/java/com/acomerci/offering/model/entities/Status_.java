package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:51.124-0300")
@StaticMetamodel(Status.class)
public class Status_ {
	public static volatile SingularAttribute<Status, Integer> id;
	public static volatile SingularAttribute<Status, String> name;
	public static volatile ListAttribute<Status, Offering> offerings;
	public static volatile ListAttribute<Status, OfferingStatus> offeringStatuses;
}
