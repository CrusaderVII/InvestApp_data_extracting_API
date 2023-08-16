package com.invest.app.data_extract.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.json_parser.SimpleJsonParser;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

public class Operator implements GetOperator{
	
	private RequestConstructor request;

	public Operator(RequestConstructor request) {
		this.request = request;
	}

	@Override
	public List<Issuer> getIssuerForLastMonth() {
        BufferedReader br = request.sendRequest(request.getRequest());

        String output;
        StringBuilder builder = new StringBuilder();
        
        List<Issuer> issuerData;
            
        try {
   			do {
               	output = br.readLine();
               	builder.append(output+'\n');
           	
              } while (!output.equals("}}"));
   			
   		} catch (IOException e) {
   			e.printStackTrace();
   			
   			request.disconnect();
   		}
   		
   		request.disconnect();
   		
   		try {
			issuerData = SimpleJsonParser.getIssuerForLastMonth(SimpleJsonParser.parse(builder.toString()), request.getSecId());
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
           
   		return issuerData;
	}
	
	@Override
	public TimePeriod getIssuerDates() {
		
		BufferedReader br = request.sendRequest(request.getDatesRequest());
		
		String output;
        StringBuilder builder = new StringBuilder();
        
        TimePeriod period;

		try {
			do {
            	output = br.readLine();
            	builder.append(output+'\n');
        	
           } while (!output.equals("}}"));
			
		} catch (IOException e) {
			e.printStackTrace();
			
			request.disconnect();
			
			period = new TimePeriod("0", "0");
		}
		
		request.disconnect();
		
		try {
			period = SimpleJsonParser.getTimePeriod(SimpleJsonParser.parse(builder.toString()));
		} catch (IOException e) {
			period = new TimePeriod("0", "0");
			
			e.printStackTrace();
		}
		
		return period;
	}

	public RequestConstructor getRequest() {
		return request;
	}

	public void setRequest(RequestConstructor request) {
		this.request = request;
	}
	
	
	
	

}
