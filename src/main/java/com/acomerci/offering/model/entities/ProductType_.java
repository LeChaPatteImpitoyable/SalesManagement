package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:51.109-0300")
@StaticMetamodel(ProductType.class)
public class ProductType_ {
	public static volatile SingularAttribute<ProductType, Integer> id;
	public static volatile SingularAttribute<ProductType, String> name;
	public static volatile ListAttribute<ProductType, Product> products;
}
