package com.trade.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.services.TradeService;
import com.trade.utils.ApiHttpResponse;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;
import com.trade.utils.OutrightData;


@RestController
public class TradeController  {

    private TradeRepository tradeRepository;

	private TradeService tradeService;
	
	private CustomLogger logger;

    TradeController(TradeRepository tradeRepository, TradeService tradeService, CustomLogger logger) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
        this.logger = logger;

    }
	@PostMapping(value = "/save", consumes = "application/json")
	public ResponseEntity<ApiHttpResponse> createTrade(@RequestBody  List<OutrightData> msgData) {

		/*
		 * Authentication authentication =
		 * SecurityContextHolder.getContext().getAuthentication();
		 * 
		 * String user = authentication.getName();
		 */
		String user ="srush";
		logger.debug("TradeController createOutrightTrade: " + msgData, "-", user);

		JsonNode resultSet = JacksonUtil.mapper.createArrayNode();
		
		try {
			System.out.println("msgData------>>"+msgData);
			resultSet = tradeService.createTrade(msgData, user);
			logger.debug("TradeController resultSet " + msgData, "-", user);

			return new ResponseEntity<>(new ApiHttpResponse(true, 200, resultSet), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TradeController createOutrightTrade Exception " ,e, "-", user);
			return new ResponseEntity<>(new ApiHttpResponse(false, 0, resultSet), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
