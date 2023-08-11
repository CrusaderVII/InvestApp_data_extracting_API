package com.invest.app.data_extract.repository;

import java.util.List;

import com.invest.app.data_extract.entities.Issuer;

public interface GetOperator {
	
	public List<Issuer> getIssuerForFiveMonths();

}
