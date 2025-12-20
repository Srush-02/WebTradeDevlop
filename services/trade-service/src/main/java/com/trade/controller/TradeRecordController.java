package com.trade.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trade.services.TradeRecordService;
import com.trade.utils.ApiHttpResponse;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;


@RestController
public class TradeRecordController {

		private TradeRecordService tradeService;
		
		private CustomLogger logger;

		TradeRecordController(TradeRecordService tradeService, CustomLogger logger) {
	        this.tradeService = tradeService;
	        this.logger = logger;

	    }

	
	@GetMapping(value = "/order-monitor", produces = "application/json")
	public ResponseEntity<ApiHttpResponse> loadTradeData(
			@RequestParam("account") List<String> account, 
			@RequestParam(name = "daysFilter", defaultValue = "All", required = false) String daysFilter,
			@RequestParam("FD") String fromDate, @RequestParam("TD") String toDate,
			@RequestHeader("x-username") String user) {
		List resultSet = new ArrayList<>();
		
		logger.debug("TradeController loadTradeData : " + "account: " + account, "-", user);
		
		resultSet = tradeService.loadTradeData(account, daysFilter, fromDate, toDate, user);
		return new ResponseEntity<>(new ApiHttpResponse(true, 200, JacksonUtil.mapper.valueToTree(resultSet)),
				HttpStatus.OK);

	}
}
