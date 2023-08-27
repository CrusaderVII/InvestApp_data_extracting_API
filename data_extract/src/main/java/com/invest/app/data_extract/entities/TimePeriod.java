package com.invest.app.data_extract.entities;

import java.util.Arrays;

public class TimePeriod {
	
	private String beginning;
	private String ending;
		
	public TimePeriod(String beginning, String ending) {
		this.beginning = beginning;
		this.ending = ending;
	}
	
	public static String formatTrim(String rawString) {
		String res = Arrays.stream(rawString.split(""))
			.map(x -> Character
					.isDigit(x.charAt(0))? x : "" )
			.reduce((accumulator, i) -> accumulator+i)
			.get();
		
		return res.substring(0, 4) + "." + res.substring(4, 6) + "." + res.substring(6); 
	}

	public String getBeginning() {
		return beginning;
	}

	public void setBeginning(String beginning) {
		this.beginning = beginning;
	}

	public String getEnding() {
		return ending;
	}

	public void setEnding(String ending) {
		this.ending = ending;
	}

	@Override
	public String toString() {
		return "TimePeriod [beginning=" + beginning + ", ending=" + ending + "]";
	}
}
