package com.trade.services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.trade.utils.OutrightData;

public interface OutrightTradeService {

	JsonNode createOutrightTrade(List<OutrightData> msgData, String user);

}
