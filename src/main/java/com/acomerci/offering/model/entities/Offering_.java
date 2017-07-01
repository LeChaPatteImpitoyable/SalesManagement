package com.acomerci.offering.model.entities;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Offering.class)
public class Offering_ {
	public static volatile SingularAttribute<Offering, Bank> bank;
	public static volatile SingularAttribute<Offering, Date> creationDate;
}