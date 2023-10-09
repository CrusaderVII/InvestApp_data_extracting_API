package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.entities.PageMetadata;
import com.invest.app.data_extract.entities.TimePeriod;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;

@RestController
@RequestMapping("/api/data")
public class DataMainController {

	@GetMapping("/mains.now")
	public  List<Issuer> getMainsNow() {
		List<Issuer> issuers = Operator.getMainsNow();

		return issuers;
	}
	
	@GetMapping("/stock")
	public List<Issuer> getStockNow(@RequestParam int page) {
		List<Issuer> issuers = Operator.getIssuersOnCertainLevelNow(1);
		
		int start = 10 * (page-1);
		int end = (start/10==issuers.size()/10)? start+issuers.size()%10 : start+10;
		
		return issuers.subList(start, end);
	}
	
	@GetMapping("/stock/pages")
	public PageMetadata getPages() {
		List<IssuerMetadata> issuers = Operator.getIssuersMetadataOnCertainLevel(1);
				
		return new PageMetadata(issuers.size()/10+1);
	}

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
