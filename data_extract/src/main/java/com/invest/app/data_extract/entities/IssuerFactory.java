package com.invest.app.data_extract.entities;

public class IssuerFactory {

	public static Issuer create(String secId, String fullName, double priceLow, double priceHigh) {
		return new Issuer(secId, fullName, priceLow, priceHigh);
	}
	
	public Issuer create() {
		return new Issuer();
	}
}
