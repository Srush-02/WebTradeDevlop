package com.trade.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.mappers.MapStructMapper;

@Component
public class TradeSaveDetails {

	@Autowired
	private CustomLogger logger;
	
	@Autowired
	private TradeRepository repository;
	
	@Autowired
	private MapStructMapper mapper;
	
	public void saveTrade(List<OutrightData> msgData, String user) {


		for (OutrightData currentRow : msgData) {
			try {

				TradeDetails tradeDetails = mapper.tradeDtoToSaveToDB(currentRow);

				if (currentRow.getStatus().equalsIgnoreCase("CANCEL")  || currentRow.getStatus().equalsIgnoreCase("MODIFY")) {
					tradeDetails.setSave(false);
				}

				repository.save(tradeDetails);

			} catch (Exception e) {
				logger.error("TradeSaveDetail saveOutrightTrade problem saving trade", e, currentRow.getTradeUUID(), user);
			}

		}

	}

}
