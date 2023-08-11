package com.invest.app.data_extract.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.ranges.RangeException;

public class RequestConstructor {
	
	public static final String DEFAULT_GET_PREFIX = "https://iss.moex.com/iss/history/engines/"
			+ "stock/markets/shares/boards/TQBR/securities/";
	public static final String DEFAULT_GET_POSTFIX = ".json?iss.meta=off&iss.json=extended&callback=JSON_CALLBACK&lang=ru&iss"
			+ ".only=history&sort_column=TRADEDATE&sort_order=desc";
	
	private String secId;
	private HttpURLConnection connection;
	
	public RequestConstructor(String secId) {
		this.secId = secId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}
	
	public void disconnect() {
		connection.disconnect();
	}
	public String getRequest() {
		return DEFAULT_GET_PREFIX + secId + DEFAULT_GET_POSTFIX;
	}
	
	public BufferedReader sendRequest(){
		
		try {	
			URL url = new URL(this.getRequest());
	        
	        connection = (HttpURLConnection) url.openConnection();
	        
	        connection.setRequestMethod("GET");
	        
	        connection.setRequestProperty("Accept", "application/json");
	
	        if (connection.getResponseCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
	        }
	        
	        return new BufferedReader(new InputStreamReader((connection.getInputStream())));
		} catch (Exception e) {
			connection.disconnect();
			
			return null;
		}
		
	}

}
