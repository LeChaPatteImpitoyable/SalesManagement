package com.acomerci.offering.model.entities;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-01T18:46:50.952-0300")
@StaticMetamodel(ActivityLog.class)
public class ActivityLog_ {
	public static volatile SingularAttribute<ActivityLog, Integer> id;
	public static volatile SingularAttribute<ActivityLog, String> message;
	public static volatile SingularAttribute<ActivityLog, Timestamp> timestamp;
	public static volatile SingularAttribute<ActivityLog, Action> action;
	public static volatile SingularAttribute<ActivityLog, User> user;
}
