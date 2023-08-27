package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.entities.TimePeriod;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;

@RestController
public class MainController {

	@GetMapping("/last-month")
	public List<Issuer> getIssuer(@RequestParam String id) {		
		return Operator.getIssuerForLastMonth(id);
	}
	
	@GetMapping("/dates")
	public TimePeriod getIssuerDates(@RequestParam String id) {		
		return Operator.getIssuerDates(id);
	}
	
	@GetMapping("/history")
	public List<Issuer> getIssuerHistory(@RequestParam String id) {
		List<Issuer> list = Operator.getIssuerHistory(id);
		
		return list;
	}
	
	@GetMapping("/issuers")
	public List<IssuerMetadata> getAllIssuers() {		
		List<IssuerMetadata> list = Operator.getAllIssuersMetadata();
		
		return list;
	}
	
	@GetMapping("/issuers/level")
	public List<IssuerMetadata> getAllIssuersOnCertainLevel(@RequestParam int level) {
		List<IssuerMetadata> issuers = Operator.getIssuersMetadataOnCertainLevel(level);
		
		return issuers;
	}
}
