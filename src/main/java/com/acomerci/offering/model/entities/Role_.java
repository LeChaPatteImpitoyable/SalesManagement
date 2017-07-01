package com.acomerci.offering.model.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Role.class)
public class Role_ {
	public static volatile SingularAttribute<Role, Integer> active;
	public static volatile SingularAttribute<Role, Bank> bank;
	public static volatile SingularAttribute<Role, String> name;
}