package com.trade.utils;

import java.util.List;
import java.util.Map;

import com.trade.Repository.TraderRepository;
import com.trade.entity.TradeDetails;

public class TradeSaveDetail {

	private CustomLogger logger;
	
	private TraderRepository repo;
	
	public void saveOutrightTrade(List<OutrightData> msgData, Map<String, OutrightData> outrightTaderMap, String user) {


		for (OutrightData currentRow : msgData) {
			try {
				TradeDetails tradeDetails = dbEntityConverter.orderDtoToSaveToDB(currentRow);

				/*
				 * if (orderMessageType == orderMsgType.CANCEL || orderMessageType ==
				 * orderMsgType.MODIFY) { tradeDetails.setSave(false); }
				 */

				repo.save(tradeDetails);
				currentRow.setOrderStatus(currentRow.trad.SUCCESS);

			} catch (Exception e) {
				logger.error("TradeSaveDetail saveOutrightTrade problem saving trade", e, currentRow.getTradeUUID(), user);
			}

		}

	}
}
