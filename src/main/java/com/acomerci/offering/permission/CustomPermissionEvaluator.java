package com.acomerci.offering.permission;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import com.acomerci.offering.model.entities.CurrentUser;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return ((CurrentUser)authentication.getPrincipal()).hasPermission(permission);
    }

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return ((CurrentUser)authentication.getPrincipal()).hasPermission(permission);
	}
}