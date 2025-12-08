package com.trade.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.trade.services.OutrightTradeService;
import com.trade.utils.ApiHttpResponse;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;
import com.trade.utils.OutrightData;

@RestController
public class TradeController {
	
	private OutrightTradeService tradeService;
	
	private CustomLogger logger;
	
	public ResponseEntity<ApiHttpResponse> createOutrightTrade(@RequestBody  List<OutrightData> msgData, @RequestHeader("x-username") String user) {

		logger.debug("TradeController createOutrightTrade " + msgData, "-", user);

		JsonNode resultSet = JacksonUtil.mapper.createArrayNode();
		
		
		try {
			resultSet = tradeService.createOutrightTrade(msgData, user);
			return new ResponseEntity<>(new ApiHttpResponse(true, 200, resultSet), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("OutrightTradeController createOutrightTrade Exception " ,e, "-", user);
			return new ResponseEntity<>(new ApiHttpResponse(false, 0, resultSet), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
