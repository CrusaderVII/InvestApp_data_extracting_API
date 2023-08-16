package com.invest.app.data_extract.json_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerFactory;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

public class SimpleJsonParser {
	
	public static ObjectMapper mapper = getObjectMapper();
		
	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper;
	}
	
	public static JsonNode parse(String json) throws IOException {
		
		return mapper.readTree(json);
	}
	
	public static TimePeriod getTimePeriod(JsonNode jsonNode) {
		JsonNode innerNode = jsonNode.get("dates");
		
		JsonNode values = innerNode.get("data");
		
		System.out.println(values.get(0).get(0));
		
		return new TimePeriod(values.get(0).get(0).textValue(), values.get(0).get(1).textValue());
	}
	
	public static List<Issuer> getIssuerForLastMonth(JsonNode jsonNode, String secId) {
		JsonNode innerNode = jsonNode.get("history");
		
		List<String> fields = new ArrayList<>();
		List<Issuer> issuers = new ArrayList<>();
		
		innerNode.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fields
					.add(fieldName.asText()));
		
		int nameIndex = fields.indexOf("SHORTNAME");
		int lowIndex  = fields.indexOf("LOW");
		int highIndex = fields.indexOf("HIGH");
		
		ArrayNode issuerData = (ArrayNode) innerNode.get("data");
		
		for (int i = 0; i < 30; i++) {
			JsonNode issuerDate = issuerData.get(i); 
			
			issuers.add(IssuerFactory.create(secId, 
					issuerDate.get(nameIndex).asText(),
					issuerDate.get(lowIndex).asDouble(),
					issuerDate.get(highIndex).asDouble()));
		}
		
		return issuers;
	}
	
	public static Issuer getIssuerNow(JsonNode jsonNode, String secId) {
		JsonNode innerNodeMarketData = jsonNode.get("marketdata");
		JsonNode innerNodeIssuerData = jsonNode.get("securities");
		
		List<String> fieldsMarket = new ArrayList<>();
		List<String> fieldsIssuer = new ArrayList<>();

		
		innerNodeMarketData.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fieldsMarket
					.add(fieldName.asText()));
		
		innerNodeIssuerData.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fieldsIssuer
					.add(fieldName.asText()));

		int nameIndex = fieldsIssuer.indexOf("SHORTNAME");
		int lowIndex  = fieldsMarket.indexOf("LOW");
		int highIndex = fieldsMarket.indexOf("HIGH");
		int nowIndex = fieldsMarket.indexOf("LAST");
		
		return new Issuer(secId,
				innerNodeIssuerData.get("data").get(0).get(nameIndex).asText(),
				innerNodeMarketData.get("data").get(0).get(lowIndex).asDouble(),
				innerNodeMarketData.get("data").get(0).get(highIndex).asDouble(),
				innerNodeMarketData.get("data").get(0).get(nowIndex).asDouble());
	}
}
