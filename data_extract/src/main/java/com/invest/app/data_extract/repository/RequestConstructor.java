package com.invest.app.data_extract.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultClientConnectionReuseStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.invest.app.data_extract.repository.default_requests.Postfix;
import com.invest.app.data_extract.repository.default_requests.Prefix;

public class RequestConstructor {
	
	private String secId;
		
	public RequestConstructor() {
			
	}
	
	public RequestConstructor(String secId) {
		this.secId = secId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}
	
	public static String getRequest(String secId) {
		return Prefix.DEFAULT_GET_HISTORY_PREFIX.value() + secId + Postfix.DEFAULT_GET_FOR_LAST_MONTH_POSTFIX.value();
	}
	
	public static String getDatesRequest(String secId) {
		return Prefix.DEFAULT_GET_HISTORY_PREFIX.value() + secId + Postfix.DEFAULT_GET_DATES_POSTFIX.value();
	}
	
	public String getNowRequest() {
		return Prefix.DEFAULT_GET_NOW_PREFIX.value() + secId + Postfix.DEFAULT_GET_NOW_POSTFIX.value();
	}
	
	public static String getHistroyRequest(int page, String secId) {
		return Prefix.DEFAULT_GET_HISTORY_PREFIX.value() + secId + Postfix.DEFAULT_GET_HISTORY_POSTFIX.value() + page;
	}
	
	public static String getHistoryCursorRequest(String secId) {
		return Prefix.DEFAULT_GET_HISTORY_PREFIX.value() + secId + Postfix.DEFAULT_GET_HISTORY_CURSOR_POSTFIX.value();
	}
	
	public static String getAllIssuersRequest() {
		return Prefix.DEFAULT_GET_ALL_ISSUERS_PREFIX.value() + Postfix.DEFAULT_JSON_EXTENSION.value();
	}
	
	public static String getNowRequest(String secId) {
		return Prefix.DEFAULT_GET_NOW_PREFIX.value() + secId + Postfix.DEFAULT_GET_NOW_POSTFIX.value();
	}
	
	public static String getUsersIssuerNow(Long id) {
		return Prefix.DEFAULT_GET_USER_ISSUERS_PREFIX.value() + Postfix.DEFAULT_GET_USER_ISSUERS_POSTFIX.value() + id.toString();
	}
	
	public static BufferedReader getPlainJson(String request) {
		RestTemplate template = new RestTemplate();
		
		ResponseEntity<String> responseEntity = template.getForEntity(request, String.class);
				
		return new BufferedReader(new StringReader(responseEntity.getBody()));
	}

}
