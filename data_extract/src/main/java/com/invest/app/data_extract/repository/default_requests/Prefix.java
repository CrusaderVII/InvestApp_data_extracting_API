package com.invest.app.data_extract.repository.default_requests;

public enum Prefix {
	DEFAULT_GET_HISTORY_PREFIX ("http://iss.moex.com/iss/history/engines/stock/markets/shares/securities/"),
	
	DEFAULT_GET_NOW_PREFIX ("https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/");

	private final String value;
	
	Prefix(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
