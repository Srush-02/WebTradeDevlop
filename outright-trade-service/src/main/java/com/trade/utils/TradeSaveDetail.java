package com.trade.utils;

import java.util.List;
import java.util.Map;

import com.trade.Repository.TraderRepository;
import com.trade.entity.TradeDetails;
import com.trade.mapper.MapStructMapper;

public class TradeSaveDetail {

	private CustomLogger logger;
	
	private TraderRepository repository;
	
	private MapStructMapper mapper;
	
	public void saveOutrightTrade(List<OutrightData> msgData, Map<String, OutrightData> outrightTaderMap, String user) {


		for (OutrightData currentRow : msgData) {
			try {

				TradeDetails tradeDetails = mapper.tradeDtoToSaveToDB(currentRow);

				if (currentRow.getStatus().equalsIgnoreCase("CANCEL")  || currentRow.getStatus().equalsIgnoreCase("MODIFY")) {
					tradeDetails.setSave(false);
				}

				repository.save(tradeDetails);
				//currentRow.setOrderStatus(currentRow.trad.SUCCESS);

			} catch (Exception e) {
				logger.error("TradeSaveDetail saveOutrightTrade problem saving trade", e, currentRow.getTradeUUID(), user);
			}

		}

	}
}
