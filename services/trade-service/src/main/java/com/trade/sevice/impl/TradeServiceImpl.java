package com.trade.sevice.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trade.services.TradeService;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;
import com.trade.utils.OutrightData;
import com.trade.utils.TradeSaveDetails;
import com.trade.utils.ValidateTrade;

import lombok.AllArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

@Service
@AllArgsConstructor
public class TradeServiceImpl implements TradeService {
	
	
	private CustomLogger logger;

	
	private ValidateTrade validateData;

	 
	private TradeSaveDetails tradeDetails;
	

	@Override
	public JsonNode createTrade(List<OutrightData> msgData, String userName) {

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

		try {
			logger.debug("TradeController validate " + msgData, "-", userName);

			ObjectNode validationObj = (ObjectNode) finalValidation(msgData, userName);

			resultSet = validationObj.size() == 0 ? JacksonUtil.mapper.createObjectNode() : validationObj;
			resultSet.put("VALID_ORDER_FIELD", "");
			resultSet.put("USER_LOGIN_FIELD", true);

			logger.debug("TradeServiceImpl createTrade after finalValidation" + resultSet.toString(), "-", userName);
System.out.println("size--------------"+validationObj.size());
			if (validationObj.size() != 0) {
				return resultSet;
			}
			System.out.println("size-----after---------");

			tradeDetails.saveTrade(msgData, userName);
		} catch (Exception e) {
			logger.error("TradeServiceImpl createTrade Exception ", e, "-", userName);
		}
		return resultSet;
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
		System.out.println("check finalValidation: " + resultSet);

		return resultSet;
	}


}
