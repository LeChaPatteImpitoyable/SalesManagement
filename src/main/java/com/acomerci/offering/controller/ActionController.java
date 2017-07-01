package com.acomerci.offering.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acomerci.offering.model.business.ManageActionBF;
import com.acomerci.offering.model.entities.Action;

@Controller
public class ActionController {
	
	public ActionController() {
	}
	
	@RequestMapping(value="/getActions", method = RequestMethod.GET)
	public @ResponseBody List<Action> getActions() {
		List<Action> actions = ManageActionBF.getInstance().getAll();
		return actions;
	}
}
