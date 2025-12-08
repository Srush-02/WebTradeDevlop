package com.trade.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trade.OutrightTradeServiceApplication;
import com.trade.services.OutrightTradeService;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;
import com.trade.utils.OutrightData;
import com.trade.utils.TradeSaveDetail;
import com.trade.utils.ValidateTrade;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OutightTradeServiceImpl implements OutrightTradeService {

	private CustomLogger logger;
	
	private ValidateTrade validateData;
	
	private TradeSaveDetail tradeDetails;
	
	
	@Override
	public JsonNode createOutrightTrade(List<OutrightData> msgData, String userName) {

		String localOrderStatus = msgData.get(0).getStatus();
		if (localOrderStatus.equalsIgnoreCase("NEW")) {
			return createTrade(msgData, userName, new HashMap<>());
		} else if (localOrderStatus.equalsIgnoreCase("AMEND")) {
			return null;
			// amendTrade(msgData,userName);
		} else {
			return null;
			// cancelTrade(msgData,userName);
		}
	}

	private JsonNode createTrade(List<OutrightData> msgData, String userName, HashMap hashMap) {

		ObjectNode resultSet = JacksonUtil.mapper.createObjectNode();

		ObjectNode validationObj = (ObjectNode) finalValidation(msgData, userName);

		System.out.println("check validation: " +validationObj);
		resultSet = validationObj.size() == 0 ? JacksonUtil.mapper.createObjectNode() : validationObj;
		resultSet.put("VALID_ORDER_FIELD", "");
		resultSet.put("USER_LOGIN_FIELD", true);

		logger.debug(" OutightTradeServiceImpl createTrade after finalValidation" + resultSet.toString(), "-", userName);

		if (validationObj.size() != 0) {
			return resultSet;
		}
		return validationObj;
		

		//tradeDetails.saveOutrightTrades(msgData, optionOrderMap, userName);	

	}

	private ObjectNode finalValidation(List<OutrightData> msgData, String userName) {

		// TODO rowErrors for row wise

		ObjectNode resultSet = JacksonUtil.mapper.createObjectNode();

		Iterator<OutrightData> itr = msgData.iterator();

		int row = 0;
		while (itr.hasNext()) {

			OutrightData currentRow = itr.next();
			ObjectNode rowErrors = JacksonUtil.mapper.createObjectNode();
			validateData.finalCommonDetailValidation(currentRow, rowErrors, userName);

			if (rowErrors.size() != 0) {
				resultSet.set(String.valueOf(row), rowErrors);
			}
			row++;
		}
		System.out.println("check finalValidation: " +resultSet);

		return resultSet;
	}

}
