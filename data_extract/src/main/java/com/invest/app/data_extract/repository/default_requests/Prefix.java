package com.invest.app.data_extract.repository.default_requests;

public enum Prefix {
	DEFAULT_GET_PREFIX ("http://iss.moex.com/iss/history/engines/stock/markets/shares/securities/");

	private final String value;
	
	Prefix(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
