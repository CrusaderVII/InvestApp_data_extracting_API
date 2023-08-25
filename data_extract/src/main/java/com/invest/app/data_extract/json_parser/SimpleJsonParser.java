package com.invest.app.data_extract.json_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.entities.IssuerFactory;
import com.invest.app.data_extract.entities.IssuerMetadata;
import com.invest.app.data_extract.repository.Operator;
import com.invest.app.data_extract.repository.RequestConstructor;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

import ch.qos.logback.core.pattern.parser.Node;

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
		
		return IssuerFactory.create(secId,
				innerNodeIssuerData.get("data").get(0).get(nameIndex).asText(),
				innerNodeMarketData.get("data").get(0).get(lowIndex).asDouble(),
				innerNodeMarketData.get("data").get(0).get(highIndex).asDouble(),
				innerNodeMarketData.get("data").get(0).get(nowIndex).asDouble());
	}
	
	public static List<Issuer> getIssuerHistory(JsonNode jsonNode, String secId, int current, int total) {
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
		int dateIndex = fields.indexOf("TRADEDATE");
		
		String startingDateString = innerNode.get("data")
				.get(0)	
				.get(dateIndex)
				.asText();
		
		for (int i = 0; i < (current+100 > total ? total-current : 100); i+=1) {
			JsonNode issuerDate = innerNode.get("data").get(i); 
			
			String currentIteratorDate = issuerDate.get(dateIndex).asText();
			
			if(currentIteratorDate.substring(currentIteratorDate.length()-3)
				.equals(startingDateString.substring(currentIteratorDate.length()-3))) {
				
				issuers.add(IssuerFactory.create(secId, 
					issuerDate.get(nameIndex).asText(),
					issuerDate.get(lowIndex).asDouble(),
					issuerDate.get(highIndex).asDouble(),
					issuerDate.get(dateIndex).asText()));
			}
		}
		
		return issuers;
	}
	
	public static int getPageNumber(JsonNode jsonNode) {
		JsonNode innerNode = jsonNode.get("history.cursor");

		List<String> fields = new ArrayList<>();
		
		innerNode.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fields
					.add(fieldName.asText()));

		int index = fields.indexOf("TOTAL");

		return innerNode.get("data")
				.get(0)
				.get(index)
				.asInt();
	}
	
	public static List<IssuerMetadata> getAllIssuersSecId(JsonNode jsonNode) {
		JsonNode innerNode = jsonNode.get("securities");
				
		List<IssuerMetadata> allIssuersMetadata = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		
		innerNode.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fields.add(fieldName.asText()));

		int secIdIndex = fields.indexOf("SECID");
		int nameIndex = fields.indexOf("SHORTNAME");
		
		innerNode.get("data")
			.iterator()
			.forEachRemaining(node -> allIssuersMetadata.add(new IssuerMetadata(
					node.get(secIdIndex).asText(),
					node.get(nameIndex).asText())));
		
		return allIssuersMetadata;
	}
	
	public static List<Issuer> getUserIssuersNow(JsonNode jsonNode) {		
		List<IssuerMetadata> issuerMetadatas = new ArrayList<>();
		
		RequestConstructor requestConstructor = new RequestConstructor();
		Operator operator = new Operator(requestConstructor);
		
		
		jsonNode.iterator()
			.forEachRemaining(innerNode -> issuerMetadatas
					.add(new IssuerMetadata(innerNode.get("secId").asText(),
											innerNode.get("fullName").asText())));

		
		List<Issuer> issuers = issuerMetadatas.stream()
			.map(issuerMetadata -> operator.getIssuerNow(issuerMetadata.getSecId()))
			.toList();
		
		return issuers;
	}
	
	public static List<IssuerMetadata> getIssuerMetadatasOnCertainLevel(JsonNode jsonNode, int level) {
		JsonNode innerNode = jsonNode.get("securities");
		
		List<IssuerMetadata> allIssuersMetadata = new ArrayList<>();
		List<String> fields = new ArrayList<>();
		Map<IssuerMetadata, Integer> allIssuersMetadataWithLevel = new HashMap<>();
		
		innerNode.get("columns")
			.iterator()
			.forEachRemaining(fieldName -> fields.add(fieldName.asText()));

		int secIdIndex = fields.indexOf("SECID");
		int nameIndex = fields.indexOf("SHORTNAME");
		int levelIndex = fields.indexOf("LISTLEVEL");
		int typeIndex = fields.indexOf("SECTYPE");
		
		innerNode.get("data")
			.iterator()
			.forEachRemaining(node -> {
				
					if (node.get(levelIndex).asInt()==level && node.get(typeIndex).asText().equals("1")) {
						allIssuersMetadata.add(new IssuerMetadata(
							node.get(secIdIndex).asText(),
							node.get(nameIndex).asText()));
					}
				}
			);
			
			
		
		for (Entry<IssuerMetadata, Integer> entry : allIssuersMetadataWithLevel.entrySet()) {
			if(entry.getValue()==level) allIssuersMetadata.add(entry.getKey());
		}
		
		return allIssuersMetadata;
	}
}
