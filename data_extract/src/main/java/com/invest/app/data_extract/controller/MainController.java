package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

@RestController
public class MainController {

	@GetMapping("/last_month/{id}")
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
		
		Issuer issuer = new Operator(requestConstructor).getIssuerNow();
		
		return issuer;
	}
	
	@GetMapping("/now/all")
	public List<Issuer> getAllIssuersNow(@RequestParam int page) {
		RequestConstructor requestConstructor = new RequestConstructor();
		Operator operator = new Operator(requestConstructor);
		
		List<IssuerMetadata> list = operator.getAllIssuersMetadata();
		
		List<Issuer> listIssuers = list.stream()
				.map(issuer -> operator.getIssuerNow(issuer.getSecId()))
				.toList();
		
		int begin = (page-1) * 10;
		int end = (page-1) * 10 + 10;
		
		return listIssuers.subList(begin, end >= listIssuers.size() ? listIssuers.size()-1 : end);
	}
	
	@GetMapping("/history/{id}")
	public List<Issuer> getIssuerHistory(@PathVariable String id) {
		RequestConstructor requestConstructor = new RequestConstructor(id);
		
		List<Issuer> list = new Operator(requestConstructor).getIssuerHistory();
		
		return list;
	}
	
	@GetMapping("/issuers")
	public List<IssuerMetadata> getAllIssuers() {
		RequestConstructor requestConstructor = new RequestConstructor();
		
		List<IssuerMetadata> list = new Operator(requestConstructor).getAllIssuersMetadata();
		
		return list;
	}
}
