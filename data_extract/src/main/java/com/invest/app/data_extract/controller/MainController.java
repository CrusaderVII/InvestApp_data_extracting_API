package com.invest.app.data_extract.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;

@RestController
public class MainController {

	@GetMapping("/")
	public void getIssuer() {
		RequestConstructor requestConstructor = new RequestConstructor("LKOH");
		
		new Operator(requestConstructor).getIssuerForFiveMonths()
			.stream()
			.forEach(System.out::println);
	}
}
