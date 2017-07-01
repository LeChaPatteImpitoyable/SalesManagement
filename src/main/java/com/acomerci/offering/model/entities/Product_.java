package com.acomerci.offering.model.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public class Product_ {
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Offering> offering;
}