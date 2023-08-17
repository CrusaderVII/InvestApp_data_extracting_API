package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

@RestController
public class MainController {

	@GetMapping("/{id}")
	public List<Issuer> getIssuer(@PathVariable String id) {
		RequestConstructor requestConstructor = new RequestConstructor(id);
		
		return new Operator(requestConstructor).getIssuerForLastMonth();
	}
	
	@GetMapping("/dates/{id}")
	public TimePeriod getIssuerDates(@PathVariable String id) {
		RequestConstructor requestConstructor = new RequestConstructor(id);
		
		return new Operator(requestConstructor).getIssuerDates();
	}
	
	@GetMapping("/now/{id}")
	public Issuer getIssuerNow(@PathVariable String id) {
		RequestConstructor requestConstructor = new RequestConstructor(id);
		
		return new Operator(requestConstructor).getIssuerNow();
	}
	
	@GetMapping("/history/{id}")
	public List<Issuer> getIssuerHistory(@PathVariable String id) {
		RequestConstructor requestConstructor = new RequestConstructor(id);
		
		List<Issuer> list = new Operator(requestConstructor).getIssuerHistory();
		
		System.out.println(list.size());
		return list;
	}
}
