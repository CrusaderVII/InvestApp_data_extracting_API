package com.invest.app.data_extract.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.ranges.RangeException;

import com.invest.app.data_extract.repository.default_requests.Postfix;
import com.invest.app.data_extract.repository.default_requests.Prefix;

public class RequestConstructor {
	
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
		return Prefix.DEFAULT_GET_PREFIX.value() + secId + Postfix.DEFAULT_GET_FOR_LAST_MONTH_POSTFIX.value();
	}
	
	public String getDatesRequest() {
		return Prefix.DEFAULT_GET_PREFIX.value() + secId + Postfix.DEFAULT_GET_DATES_POSTFIX.value();
	}
	
	public BufferedReader sendRequest(String request){
		
		try {	
			URL url = new URL(request);
	        
	        connection = (HttpURLConnection) url.openConnection();
	        
	        connection.setRequestMethod("GET");
	        
	        connection.setRequestProperty("Accept", "application/json");
	
	        if (connection.getResponseCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
	        }
	        
	        return new BufferedReader(new InputStreamReader((connection.getInputStream())));
		} catch (Exception e) {
			
			System.out.println(e);
			connection.disconnect();
			
			return null;
		}
		
	}

}
