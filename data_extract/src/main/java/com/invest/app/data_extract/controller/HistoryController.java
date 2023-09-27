package com.invest.app.data_extract.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.Operator;

@RestController
@RequestMapping("/api/data/history")
public class HistoryController {
	
	@GetMapping("/last/month")
	public List<Issuer> getIssuerForLastMonth(@RequestParam String secId) {
		List<Issuer> issuerForLastMonth = Operator.getIssuerForLastMonth(secId);
		
		return issuerForLastMonth;
	}
	
	@GetMapping("/last/week")
	public List<Issuer> getIssuerForLastWeek(@RequestParam String secId) {
		List<Issuer> issuerForLastWeekIssuers = Operator.getIssuerForLastWeek(secId);
		
		return issuerForLastWeekIssuers;
	}
}
