package com.trade.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.services.TradeService;
import com.trade.utils.ApiHttpResponse;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;
import com.trade.utils.OutrightData;

import tools.jackson.databind.JsonNode;

@RestController
public class TradeController  {

    private final TradeRepository tradeRepository;

	private TradeService tradeService;
	
	private CustomLogger logger;

    TradeController(TradeRepository tradeRepository, CustomLogger logger) {
        this.tradeRepository = tradeRepository;
        this.logger = logger;

    }
	@PostMapping(value = "/save", consumes = "application/json")
	public ResponseEntity<ApiHttpResponse> createOutrightTrade(@RequestBody  List<OutrightData> msgData, @RequestHeader("x-username") String user) {

		logger.debug("TradeController createOutrightTrade: " + msgData, "-", user);

		JsonNode resultSet = JacksonUtil.mapper.createArrayNode();
		
		
		try {
			resultSet = tradeService.createTrade(msgData, user);
			logger.debug("TradeController resultSet " + msgData, "-", user);

			System.out.println("resultSet");
			return new ResponseEntity<>(new ApiHttpResponse(true, 200, resultSet), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("TradeController createOutrightTrade Exception " ,e, "-", user);
			return new ResponseEntity<>(new ApiHttpResponse(false, 0, resultSet), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
