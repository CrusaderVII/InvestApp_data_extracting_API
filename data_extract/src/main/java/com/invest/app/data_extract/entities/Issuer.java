package com.invest.app.data_extract.entities;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Issuer {
 
	private String secId;
	private String fullName;
	private double priceLow;
	private double priceHigh;
	private double priceNow;
	private double priceOpen;
	private double percent;
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
	
	public Issuer(String secId, String fullName, double priceOpen, String date) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceOpen = priceOpen;
		this.date = date;
	}
	
	public Issuer(String secId, String fullName, String date, double priceOpen, double priceNow, double change) {
		this.secId = secId;
		this.fullName = fullName;
		this.priceNow = priceNow;
		this.priceOpen = priceOpen;
		this.percent = calculatePercent(priceOpen, change);
		this.date = date;
	}
	
	private double calculatePercent(double priceOpen, double change) {
		double percent = change/priceOpen * 100;
		
		return Double.parseDouble(String.format("%.2f", percent).replace(',', '.'));
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

	public double getPriceOpen() {
		return priceOpen;
	}

	public void setPriceOpen(double priceOpen) {
		this.priceOpen = priceOpen;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "Issuer [secId=" + secId + ", fullName=" + fullName + ", priceLow=" + priceLow + ", priceHigh="
				+ priceHigh + (this.priceNow == 0? "" : "priceNow="+this.priceNow) + "]";
	}
		
	
}
