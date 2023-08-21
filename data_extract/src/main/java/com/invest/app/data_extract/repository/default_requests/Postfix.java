package com.invest.app.data_extract.repository.default_requests;

public enum Postfix {
	DEFAULT_GET_FOR_LAST_MONTH_POSTFIX (".json?iss.meta=off&"
			+ ".only=history&sort_column=TRADEDATE&sort_order=desc"),
	
	DEFAULT_GET_DATES_POSTFIX ("/dates.json"),
	
	DEFAULT_GET_NOW_POSTFIX (".json?iss.meta=off"),
	
	DEFAULT_GET_HISTORY_POSTFIX (".json?iss.meta=off&start="),
	
	DEFAULT_GET_HISTORY_CURSOR_POSTFIX (".json?iss.meta=off&iss.only=history.cursor"),
	
	DEFAULT_JSON_EXTENSION (".json");
	
	private final String value;
	
	private Postfix(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}

}
