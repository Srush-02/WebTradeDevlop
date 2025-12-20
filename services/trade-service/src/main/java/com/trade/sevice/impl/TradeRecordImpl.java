package com.trade.sevice.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.mappers.MapStructMapper;
import com.trade.services.TradeRecordService;
import com.trade.utils.OutrightData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeRecordImpl implements TradeRecordService{

	private final MapStructMapper mapper;
		
	private final TradeRepository repo;
	
	public static final DateTimeFormatter longDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	@Override
	public List loadTradeData(List<String> accountlist, String daysFilter, String fromDate, String toDate, String user) {
		List<TradeDetails> tradeDetailList = new ArrayList<>();
		List<OutrightData> returnList = new ArrayList<>();

		tradeDetailList = getDateBasedTrades(fromDate, toDate, accountlist, tradeDetailList, user);
		returnList.addAll(mapper.tradeDetailListToreturnList(tradeDetailList));
System.out.println("returnList---->>"+returnList);
		return returnList;
	}

	private List<TradeDetails> getDateBasedTrades(String fromDateStr, String toDateStr, List<String> accountlist,
			List<TradeDetails> tradeDetailList, String user) {

		LocalDate fromDateLocalDate = LocalDate.parse(fromDateStr, longDateFormatter);
		LocalDate toDateLocalDate = LocalDate.parse(toDateStr, longDateFormatter);
		long fromDate = fromDateLocalDate
		        .atStartOfDay(ZoneId.of("UTC"))
		        .toInstant()
		        .toEpochMilli();

		long toDate = toDateLocalDate
		        .atTime(LocalTime.MAX)
		        .atZone(ZoneId.of("UTC"))
		        .toInstant()
		        .toEpochMilli();
		System.out.println("toDate---->>"+toDate+ "fromDate---->>"+fromDate);

		if (!accountlist.isEmpty()) {
			List<String> listToSend = null;
			while (!accountlist.isEmpty()) {
				listToSend = accountlist.stream().limit(1000).collect(Collectors.toList());
				tradeDetailList.addAll(repo.findByBuyerInAndCreatedTimestampBetween(listToSend, fromDate, toDate));//TODO need to check
				accountlist = accountlist.stream().skip(1000).collect(Collectors.toList());
			}

		} else {
			 tradeDetailList.addAll(repo.findByCreatedTimestampBetween(fromDate, toDate));
		}
		System.out.println("tradeDetailList---->>"+tradeDetailList);

	    return tradeDetailList;
	}

}
