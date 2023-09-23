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
import com.invest.app.data_extract.entities.TimePeriod;
import com.invest.app.data_extract.json_parser.SimpleJsonParser;

public class Operator{

	public Operator(RequestConstructor request) {
		
	}

	public static List<Issuer> getIssuerForLastMonth(String secId) {
        BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getRequest(secId));
        
        List<Issuer> issuerData;
   		
   		try {
			issuerData = SimpleJsonParser
					.getIssuerForPeriod(SimpleJsonParser
							.parse(readJson(br)), secId, 30);
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
           
   		return issuerData;
	}
	
	public static List<Issuer> getIssuerForLastWeek(String secId) {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getRequest(secId));
        
        List<Issuer> issuerData;
   		
   		try {
			issuerData = SimpleJsonParser
					.getIssuerForPeriod(SimpleJsonParser
							.parse(readJson(br)), secId, 7);
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
           
   		return issuerData;
	}
	
	public static TimePeriod getIssuerDates(String secId) {
		
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getDatesRequest(secId));
        
        TimePeriod period;
		
		try {
			period = SimpleJsonParser.getTimePeriod(SimpleJsonParser.parse(readJson(br)));
		} catch (IOException e) {
			period = new TimePeriod("0", "0");
			
			e.printStackTrace();
		}
		
		return period;
	}
	
	public static Issuer getIssuerNow(String secId) {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getNowRequest(secId));
		
		Issuer issuer;
		
		try {
			issuer = SimpleJsonParser
					.getIssuerNow(SimpleJsonParser
							.parse(readJson(br)), secId);
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		return issuer;
	}
	
	public static Issuer getIssuerNowWithPercent(String secId) {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getNowRequest(secId));
		
		Issuer issuer;
		
		try {
			issuer = SimpleJsonParser
					.getIssuerNowWithPercent(SimpleJsonParser
							.parse(readJson(br)), secId);
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		return issuer;
	}

	public static List<Issuer> getIssuerHistory(String secId) {
		List<Issuer> issuerHistory = new ArrayList<>();
		
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getHistoryCursorRequest(secId));
        
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
        	issuerHistory.addAll(getIssuerHistoryOnPage(current, total, secId));
        	
			current += 100;
		}
		
		return issuerHistory;
	}
	
	public static List<Issuer> getIssuerHistoryOnPage(int current, int total, String secId) {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getHistroyRequest(current, secId));
        
        List<Issuer> issuerData;
        
        try {
			issuerData = SimpleJsonParser
					.getIssuerHistory(SimpleJsonParser
							.parse(readJson(br)), secId, current, total);
		} catch (IOException e) {
			issuerData = null;
			
			e.printStackTrace();
		}
		
		return issuerData;
	}
	
	public static List<IssuerMetadata> getAllIssuersMetadata() {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getAllIssuersRequest());
		
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
	
	public static List<IssuerMetadata> getIssuersMetadataOnCertainLevel(int level) {
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getAllIssuersRequest());
		
		List<IssuerMetadata> issuersMetadata = new ArrayList<>();
                
        try {
			issuersMetadata = SimpleJsonParser
					.getIssuerMetadatasOnCertainLevel(SimpleJsonParser
								.parse(readJson(br)),
							level);
		} catch (IOException e) {
			issuersMetadata = null;
			
			e.printStackTrace();
		}
        
        return issuersMetadata;
	}
	
	public static List<Issuer> getCertainIssuersNow(Long id) {
		
		BufferedReader br = RequestConstructor.getPlainJson(RequestConstructor.getUsersIssuerNow(id));
		
		List<Issuer> issuers;
		try {
			issuers = SimpleJsonParser
					.getUserIssuersNow(SimpleJsonParser
							.parse(readJson(br)));
		} catch (IOException e) {
			issuers = null;
			
			e.printStackTrace();
		}
				
		return issuers;
	}
	
	public static List<Issuer> getIssuersOnCertainLevelNow(int level) {
		List<IssuerMetadata> metadata = Operator.getIssuersMetadataOnCertainLevel(level);
		List<Issuer> issuersNow = metadata.stream()
				.map(issuerMetadata -> Operator.getIssuerNowWithPercent(issuerMetadata.getSecId()))
				.toList();
		
		return issuersNow;
	}
	
	private static String readJson(BufferedReader br) {
		String output;
        StringBuilder builder = new StringBuilder();
		try {
   			do {
               	output = br.readLine();
               	
               	if(output!=null) builder.append(output+'\n');
               	
               	;
              } while (output != null );
   			
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
		
		return builder.toString();
	}

}
