package com.acomerci.offering.model.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(OfferingStatus.class)
public class OfferingStatus_ {
	public static volatile SingularAttribute<OfferingStatus, Integer> id;
	public static volatile SingularAttribute<OfferingStatus, Offering> offering;
}