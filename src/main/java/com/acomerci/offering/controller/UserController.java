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
import org.springframework.web.servlet.ModelAndView;

import com.acomerci.offering.model.business.ManageUserBF;
import com.acomerci.offering.model.entities.User;
import com.acomerci.offering.model.entities.UserConfig;

@Controller
public class UserController {
	
	ManageUserBF userBF = new ManageUserBF();
	
	public UserController() {
	}
	
	@RequestMapping(value="/getUsers", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsers(@RequestParam("showActive") int showActive) {
		List<User> users = userBF.getAll(showActive);
		return users;
	}
	
	@RequestMapping(value="/createUser", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody UserConfig user) {
		String insert = userBF.create(user);
		
		if (insert.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(insert, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody UserConfig user) {
		String update = userBF.update(user);
		
		if (update.isEmpty())
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(update, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteUser(@RequestBody UserConfig user) {
		boolean delete = userBF.delete(user);
		
		if (delete)
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "disabled", required = false) String disabled,
							  @RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView();
		
		if (error != null) {
			model.addObject("error", "Correo electr&oacute;nico o contrase&ntilde;a incorrecta.");
		}
		
		if (disabled != null) {
			model.addObject("error", "Usuario inactivo.");
		}

		if (logout != null) {
			model.addObject("msg", "Usuario deslogueado correctamente.");
		}
		
		model.setViewName("login");

		return model;
	}
	
	@RequestMapping(value = "/updateUserConfig", method = RequestMethod.POST)
	public ResponseEntity<Object> updateUserConfig(@RequestBody UserConfig user) {
		String update = userBF.updateSelf(user);
		
		if (update.isEmpty())
			return new ResponseEntity<Object>(HttpStatus.OK);
		else
			return new ResponseEntity<Object>(update, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
