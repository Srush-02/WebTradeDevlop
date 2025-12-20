package com.trade.sevice.impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trade.services.TradeService;
import com.trade.utils.AppConstant;
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

		ObjectNode resultSet = JacksonUtil.mapper.createObjectNode();
		System.out.println("msgData--currentTradeValue---->"+msgData);

		try {
			//TODO amend and cancel validation remaning
			String tradeType = msgData.get(0).getTradeType();
			for (OutrightData currentTradeValue : msgData) {
				if (!tradeType.equalsIgnoreCase("NEW")) {
				    Instant instant = Instant.parse(currentTradeValue.getLastModifiedTimestamp());
				    long epochMillis = instant.toEpochMilli();
					System.out.println("validTrade--currentTradeValue---->"+currentTradeValue);

					String validTrade = validateData.checkLastModifiedTimeValid(currentTradeValue.getTraderUUID(), epochMillis, userName);
					
					System.out.println("validTrade------>"+validTrade);
					if (!validTrade.isEmpty()) {
						resultSet.put(AppConstant.VALID_TRADE_FIELD, validTrade);
						resultSet.put(AppConstant.USER_LOGIN_FIELD, true);
						logger.info("OrderServiceImpl createOrder checkLastModifiedTimeValid " + resultSet.toString(), currentTradeValue.getTraderUUID().toString(), userName);
						return resultSet;
					}
				}
			}
			
			ObjectNode validationObj = (ObjectNode) finalValidation(msgData, userName);

			resultSet = validationObj.size() == 0 ? JacksonUtil.mapper.createObjectNode() : validationObj;
			resultSet.put(AppConstant.VALID_TRADE_FIELD, "");
			resultSet.put(AppConstant.USER_LOGIN_FIELD, true);

			logger.debug("TradeServiceImpl createTrade after finalValidation" + resultSet.toString(), "-", userName);
				System.out.println("size--------------"+validationObj.size());
			if (validationObj.size() != 0) {
				return resultSet;
			}
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
