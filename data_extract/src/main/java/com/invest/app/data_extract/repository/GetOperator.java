package com.invest.app.data_extract.repository;

import java.util.List;

import com.invest.app.data_extract.entities.Issuer;
import com.invest.app.data_extract.repository.time_utils.TimePeriod;

public interface GetOperator {
	
	public List<Issuer> getIssuerForFiveMonths();
	
	public TimePeriod getIssuerDates();

}
