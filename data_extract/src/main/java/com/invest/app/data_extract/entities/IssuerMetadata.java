package com.invest.app.data_extract.entities;

public class IssuerMetadata {
	
	private String secId;
	private String fullName;
	
	public IssuerMetadata() {
		
	}
	
	public IssuerMetadata(String secId, String fullName) {
		this.secId = secId;
		this.fullName = fullName;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "IssuerMetadata [secId=" + secId + ", fullName=" + fullName + "]";
	}
	
	
	
}
