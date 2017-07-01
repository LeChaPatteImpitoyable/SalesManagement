package com.acomerci.offering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acomerci.offering.model.business.ManageChannelBF;
import com.acomerci.offering.model.entities.ChannelConfig;

@Controller
public class ChannelConfigController {
	ManageChannelBF channelBF = new ManageChannelBF();
	
	@RequestMapping(value="/getChannelConfig", method = RequestMethod.GET)
	public @ResponseBody ChannelConfig getConfiguration() {
		return channelBF.get();
	}
	
	@RequestMapping(value="/updateChannelConfig", method = RequestMethod.POST)
	public ResponseEntity<String> updateConfiguration(@RequestBody ChannelConfig channel) {
		if (channelBF.update(channel))
			return new ResponseEntity<String>(HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
