package com.invest.app.data_extract.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

@RestController
public class MainController {

	@GetMapping("/")
	public Issuer getIssuer() {
		RequestConstructor requestConstructor = new RequestConstructor("GAZP");
		
		return new Operator(requestConstructor).getIssuerForFiveMonths().get(0);
	}
	
	@GetMapping("/dates")
	public TimePeriod getIssuerDates() {
		RequestConstructor requestConstructor = new RequestConstructor("GAZP");
		
		return new Operator(requestConstructor).getIssuerDates();
	}
}
