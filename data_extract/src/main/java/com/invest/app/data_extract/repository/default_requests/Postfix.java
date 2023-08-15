package com.invest.app.data_extract.repository.default_requests;

public enum Postfix {
	DEFAULT_GET_POSTFIX (".json?iss.meta=off&iss.json=extended&callback=JSON_CALLBACK&lang=ru&iss"
			+ ".only=history&sort_column=TRADEDATE&sort_order=desc"),
	
	DEFAULT_GET_DATES_POSTFIX ("/dates.json?");
	
	private final String value;
	
	private Postfix(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}

}
