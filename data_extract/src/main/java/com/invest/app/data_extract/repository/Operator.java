package com.invest.app.data_extract.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.json_parser.SimpleJsonParser;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

public class Operator implements GetOperator{
	
	private RequestConstructor request;

	public Operator(RequestConstructor request) {
		this.request = request;
	}

	@Override
	public List<Issuer> getIssuerForLastMonth() {
        BufferedReader br = request.getPlainJson(request.getRequest());
        
        List<Issuer> issuerData;
   		
   		try {
			issuerData = SimpleJsonParser
					.getIssuerForLastMonth(SimpleJsonParser
							.parse(readJson(br)), request.getSecId());
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
           
   		return issuerData;
	}
	
	@Override
	public TimePeriod getIssuerDates() {
		
		BufferedReader br = request.getPlainJson(request.getDatesRequest());
        
        TimePeriod period;
		
		try {
			period = SimpleJsonParser.getTimePeriod(SimpleJsonParser.parse(readJson(br)));
		} catch (IOException e) {
			period = new TimePeriod("0", "0");
			
			e.printStackTrace();
		}
		
		return period;
	}
	
	@Override
	public Issuer getIssuerNow() {
		
		return null;
	}

	@Override
	public List<Issuer> getIssuerHistory() {
		List<Issuer> issuerHistory = new ArrayList<>();
		
		BufferedReader br = request.getPlainJson(request.getHistoryCursorRequest());
        
        int total;
        int current = 0;
        
        try {
			total = SimpleJsonParser
					.getPageNumber(SimpleJsonParser
							.parse(readJson(br)));
		} catch (IOException e) {
			total = 0;
			
			e.printStackTrace();
		}
        
        while (current <= total) {
        	issuerHistory.addAll(getIssuerHistoryOnPage(current, total));
        	
			current += 100;
		}
		
		return issuerHistory;
	}
	
	public List<Issuer> getIssuerHistoryOnPage(int current, int total) {
		BufferedReader br = request.getPlainJson(request.getHistroyRequest(current));
        
        List<Issuer> issuerData;
        
        try {
			issuerData = SimpleJsonParser
					.getIssuerHistory(SimpleJsonParser
							.parse(readJson(br)), request.getSecId(),current, total);
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
		
		return issuerData;
	}
	
	public List<IssuerMetadata> getAllIssuersMetadata() {
		BufferedReader br = request.getPlainJson(request.getAllIssuersRequest());
		
		List<IssuerMetadata> issuersMetadata = new ArrayList<>();
                
        try {
			issuersMetadata = SimpleJsonParser
					.getAllIssuersSecId(SimpleJsonParser
							.parse(readJson(br)));
		} catch (IOException e) {
			issuersMetadata = null;
			
			e.printStackTrace();
		}
        
        return issuersMetadata;
	}
	
	private String readJson(BufferedReader br) {
		String output;
        StringBuilder builder = new StringBuilder();
        
		try {
   			do {
               	output = br.readLine();
               	builder.append(output+'\n');
           	
              } while (output == null || !output.equals("}}"));
   			
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
		
		return builder.toString();
	}

	public RequestConstructor getRequest() {
		return request;
	}

	public void setRequest(RequestConstructor request) {
		this.request = request;
	}
	
	
	
	

}
