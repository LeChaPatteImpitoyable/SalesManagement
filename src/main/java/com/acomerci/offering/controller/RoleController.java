package com.acomerci.offering.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acomerci.offering.model.business.ManageRoleBF;
import com.acomerci.offering.model.entities.Role;
import com.acomerci.offering.model.entities.RoleConfig;

@Controller
public class RoleController {
	
	ManageRoleBF roleBF = new ManageRoleBF();
	
	public RoleController() {
	}
	
	@RequestMapping(value="/getRoleNames", method = RequestMethod.GET)
	public @ResponseBody List<Role> getRoleNames() {
		List<Role> roles = roleBF.getRoleNames();
		return roles;
	}
	
	@RequestMapping(value="/getRole", method = RequestMethod.GET)
	public @ResponseBody Role getRole(@RequestParam("id") int roleId) {
		Role role = roleBF.find(roleId);
		return role;
	}
	
	@RequestMapping(value="/createRole", method = RequestMethod.POST)
	public ResponseEntity<Object> createRole(@RequestBody RoleConfig role) {
		String insert = roleBF.create(role);
		
		if (insert.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(insert, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/updateRole", method = RequestMethod.POST)
	public ResponseEntity<Object> updateRole(@RequestBody RoleConfig role) {
		String update = roleBF.update(role);
		
		if (update.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(update, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/deleteRole", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteRole(@RequestBody RoleConfig role) {
		boolean delete = roleBF.delete(role);
		
		if (delete)
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
