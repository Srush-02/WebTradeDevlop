package com.trade.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
@AllArgsConstructor
public class ApiHttpResponse {
	
	boolean status ;
	int errCode ;
	JsonNode dataObject;

}
