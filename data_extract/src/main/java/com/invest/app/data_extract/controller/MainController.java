package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

@RestController
public class MainController {

	@GetMapping("/")
	public List<Issuer> getIssuer() {
		RequestConstructor requestConstructor = new RequestConstructor("GAZP");
		
		return new Operator(requestConstructor).getIssuerForLastMonth();
	}
	
	@GetMapping("/dates")
	public TimePeriod getIssuerDates() {
		RequestConstructor requestConstructor = new RequestConstructor("GAZP");
		
		return new Operator(requestConstructor).getIssuerDates();
	}
	
	@GetMapping("/now")
	public Issuer getIssuerNow() {
		RequestConstructor requestConstructor = new RequestConstructor("GAZP");
		
		return new Operator(requestConstructor).getIssuerNow();
	}
}
