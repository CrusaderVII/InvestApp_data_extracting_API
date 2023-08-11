package com.invest.app.data_extract.entities;

import java.util.Arrays;
import java.util.List;

public class Issuer {
 
	private String secId;
	private String fullName;
	private double priceLow;
	private double priceHigh;
	
	public Issuer() {
		
	}
	
	public Issuer(String secId, String fullName, double priceLow, double priceHigh) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceLow = priceLow;
		this.priceHigh = priceHigh;
	}
	
	public String getShortName() {
		return secId;
	}
	public void setShortName(String shortName) {
		this.secId = shortName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullNAme) {
		this.fullName = fullNAme;
	}
	
	public double getPriceLow() {
		return priceLow;
	}
	public void setPrice(double priceLow) {
		this.priceLow = priceLow;
	}

	public double getPriceHigh() {
		return priceHigh;
	}
	public void setPriceHigh(double priceHigh) {
		this.priceHigh = priceHigh;
	}
	
	public static Issuer convertStringToIssuer (String stringRepresentation, String secId) {
		stringRepresentation = stringRepresentation.replaceAll("\\{", "").replaceAll("\\}", "");
		
		List<String> splittedStringRepresentation = Arrays.asList(stringRepresentation.split(","));
		
		String name = splittedStringRepresentation.get(2)
				.split(" ")[2]
				.replaceAll("\"", "");
		
		String lowPrice = splittedStringRepresentation.get(7)
				.split(" ")[2]
				.replaceAll("\"", "");
				
		String highPrice = splittedStringRepresentation.get(8)
				.split(" ")[2]
				.replaceAll("\"", "");
		
		return new Issuer(secId, name, Double.parseDouble(lowPrice), Double.parseDouble(highPrice));
	}


	@Override
	public String toString() {
		return "Issuer [secId=" + secId + ", fullName=" + fullName + ", priceLow=" + priceLow + ", priceHigh="
				+ priceHigh + "]";
	}
		
	
}
