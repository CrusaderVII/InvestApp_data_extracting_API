package com.invest.app.data_extract.entities;

public class IssuerMetadata {
	
	private String secId;
	private String fullName;
	private String boardid;
	
	public IssuerMetadata() {
		
	}
	
	public IssuerMetadata(String secId, String fullName, String boardid) {
		this.secId = secId;
		this.fullName = fullName;
		this.boardid = boardid;
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

	public String getBoardid() {
		return boardid;
	}

	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}
	
	
	
	
}
