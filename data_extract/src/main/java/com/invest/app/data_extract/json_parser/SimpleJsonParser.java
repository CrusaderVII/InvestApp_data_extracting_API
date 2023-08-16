package com.invest.app.data_extract.json_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.tomcat.util.json.JSONParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	
}
