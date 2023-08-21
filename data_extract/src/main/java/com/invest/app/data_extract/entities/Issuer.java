package com.invest.app.data_extract.entities;

import java.util.Arrays;
import java.util.List;

public class Issuer {
 
	private String secId;
	private String fullName;
	private double priceLow;
	private double priceHigh;
	private double priceNow;
	private String date;
	
	public Issuer() {
		
	}
	
	public Issuer(String secId, String fullName, double priceLow, double priceHigh) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceLow = priceLow;
		this.priceHigh = priceHigh;
	}
	
	public Issuer(String secId, String fullName, double priceLow, double priceHigh, double priceNow) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceLow = priceLow;
		this.priceHigh = priceHigh;
		this.priceNow = priceNow;
	}
	
	public Issuer(String secId, String fullName, double priceLow, double priceHigh, double priceNow, String date) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceLow = priceLow;
		this.priceHigh = priceHigh;
		this.priceNow = priceNow;
		this.date = date;
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
	
	public double getPriceNow() {
		return priceNow;
	}
	public void setPriceNow(double pirceNow) {
		this.priceNow = pirceNow;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Issuer [secId=" + secId + ", fullName=" + fullName + ", priceLow=" + priceLow + ", priceHigh="
				+ priceHigh + (this.priceNow == 0? "" : "priceNow="+this.priceNow) + "]";
	}
		
	
}
