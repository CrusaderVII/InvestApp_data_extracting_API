package com.invest.app.data_extract.entities;

public class IssuerFactory {
	
	public static Issuer create(String secId, String fullName, double priceLow, double priceHigh) {
		return new Issuer(secId, fullName, priceLow, priceHigh);
	}
	
	public static Issuer create(String secId, String fullName, double priceOpen, String date) {
		return new Issuer(secId, fullName, priceOpen, date);
	}
	
	public static Issuer create(String secId, String fullName, double priceLow, double priceHigh, double priceNow) {
		return new Issuer(secId, fullName, priceLow, priceHigh, priceNow);
	}

	public static Issuer create(String secId, String fullName, double priceLow, double priceHigh, String date) {
		return new Issuer(secId, fullName, priceLow, priceHigh, 0, date);
	}
	
	public static Issuer create(String secId, String fullName, String date, double priceOpen, double priceNow, double change) {
		return new Issuer(secId, fullName, date, priceOpen, priceNow, change);
	}
	
	public static Issuer create(String secId, String fullName, double priceLow, double priceHigh, double priceNow, String date) {
		return new Issuer(secId, fullName, priceLow, priceHigh, priceNow, date);
	}
	
	public Issuer create() {
		return new Issuer();
	}
}
