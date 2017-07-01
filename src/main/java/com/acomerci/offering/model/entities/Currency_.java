package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:51.062-0300")
@StaticMetamodel(Currency.class)
public class Currency_ {
	public static volatile SingularAttribute<Currency, Integer> id;
	public static volatile SingularAttribute<Currency, String> name;
	public static volatile ListAttribute<Currency, Product> products;
}
