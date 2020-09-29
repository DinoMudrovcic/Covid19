package com.dinomudrovcic.bisscovid.models;

import java.util.List;

import lombok.Data;

@Data
public class HistoryResponse {

//	private String get;
//	private List<Object> parameters;
//	private List<Object> errors;
//	private int results;
	private List<Statistics> response;
	
}
