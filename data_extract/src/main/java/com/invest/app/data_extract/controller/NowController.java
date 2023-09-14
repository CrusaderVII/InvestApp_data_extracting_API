package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;

@RestController
@RequestMapping("/now")
public class NowController {
	
	@GetMapping("/test")
	public List<Issuer> getUserIssuers(@RequestParam Long id) {				
		List<Issuer> issuers = Operator.getCertainIssuersNow(id);
		
		return issuers;
	}
	
	@GetMapping("/all")
	public List<Issuer> getAllIssuersNow(@RequestParam int page) {
		List<IssuerMetadata> list = Operator.getAllIssuersMetadata();
		
		List<Issuer> listIssuers = list.stream()
				.map(issuer -> Operator.getIssuerNow(issuer.getSecId()))
				.toList();
		
		int begin = (page-1) * 10;
		int end = (page-1) * 10 + 10;
		
		return listIssuers.subList(begin >= listIssuers.size() ? listIssuers.size()-11 : begin, 
								   end >= listIssuers.size() ? listIssuers.size()-1 : end);
	}
	
	@PostMapping("/level/issuers")
	public List<Issuer> getIssuersOnCertainLevelNow(@RequestBody List<IssuerMetadata> list) {
		List<Issuer> issuersNow = list.stream()
				.map(issuerMetadata -> Operator.getIssuerNowWithPercent(issuerMetadata.getSecId()))
				.toList();
		
		return issuersNow;
	}
	
	@GetMapping("/issuer")
	public Issuer getIssuerNow(@RequestParam String secId) {
		
		return Operator.getIssuerNowWithPercent(secId);
	}
}
