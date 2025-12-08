package com.trade.utils;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiHttpResponse {

	boolean status ;
	int errCode ;
	JsonNode dataObject;
}
