package com.trade.services;

import java.util.List;

import com.trade.utils.OutrightData;

import tools.jackson.databind.JsonNode;

public interface TradeService {	
	
	JsonNode createTrade(List<OutrightData> msgData, String user);

}
