package com.acomerci.offering.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:50.921-0300")
@StaticMetamodel(Action.class)
public class Action_ {
	public static volatile SingularAttribute<Action, Integer> id;
	public static volatile SingularAttribute<Action, String> description;
	public static volatile SingularAttribute<Action, String> name;
	public static volatile ListAttribute<Action, Role> roles;
}
