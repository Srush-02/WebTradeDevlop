package com.trade.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.mappers.MapStructMapper;

@Component
public class TradeSaveDetails {

	private static final String NEW = null;

	private static final String MODIFY = null;

	private static final String CANCEL = null;

	@Autowired
	private CustomLogger logger;
	
	@Autowired
	private TradeRepository repository;
	
	@Autowired
	private MapStructMapper mapper;
	
	public static final DateTimeFormatter uiDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

	
	public void saveTrade(List<OutrightData> msgData, String user) {

		for (OutrightData currentRow : msgData) {
			try {
				String time = Instant.now().toEpochMilli() + "";
				String status = currentRow.getTradeType();
				LocalDate date =  getTradeDate(status);

				saveTradeDetails(currentRow, time, user, date);
				System.out.println("currentRow--trade id-"+currentRow.getTraderUUID()+ "currentRow---"+currentRow);

				TradeDetails tradeDetails = mapper.tradeDtoToSaveToDB(currentRow);
				System.out.println("tradeDetails---"+tradeDetails);
				if (status.equalsIgnoreCase("CANCEL")  || status.equalsIgnoreCase("MODIFY")) {
					tradeDetails.setSave(false);
				}
				repository.save(tradeDetails);
				currentRow.setTraderStatus("SUCCESS");


			} catch (Exception e) {
				currentRow.setTraderStatus("FAILED");
				logger.error("TradeSaveDetail saveOutrightTrade problem saving trade", e, currentRow.getTraderUUID(), user);
			}

		}

	}
	
	private LocalDate getTradeDate(String status) {
		LocalDate tradeDate = LocalDate.now(ZoneId.of("UTC"));
		/*
		 * if (status.equalsIgnoreCase("NEW") || status.equalsIgnoreCase("MODIFY")) {
		 * tradeDate =
		 * ZonedDateTime.now(ZoneId.of(.getTimeZone())).withNano(0); }
		 */
		return tradeDate;
	}
	
	public void saveTradeDetails(OutrightData currentRow, String time, String user, LocalDate tradeDate) {
		String tradeStatus = currentRow.getTradeType();
		String key = null;

		switch (tradeStatus) {
		case "NEW" :
			key = UUID.randomUUID().toString();
			currentRow.setTraderUUID(key);
			currentRow.setCreatedTimestamp(time);
			currentRow.setLastModifiedTimestamp(time);
			currentRow.setCreatedBy(user);
			currentRow.setTradeDate(tradeDate.format(uiDateFormatter));
			currentRow.setLastModifiedBy(user);
			currentRow.setLastModifiedTimestamp(time);
			break;
		case "MODIFY" :
			currentRow.setLastModifiedBy(user);
			currentRow.setLastModifiedTimestamp(time);
			break;
		case "CANCEL" :
			currentRow.setLastModifiedBy(user);//TODO need to check
			currentRow.setLastModifiedTimestamp(time);
			break;
		default:
			break;
		}
		currentRow.setTraderStatus("EXECUTING");

	}

}
