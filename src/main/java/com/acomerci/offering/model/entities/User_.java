package com.acomerci.offering.model.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> active;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Bank> bank;
}