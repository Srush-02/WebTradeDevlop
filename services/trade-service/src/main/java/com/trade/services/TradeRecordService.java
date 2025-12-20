package com.trade.services;

import java.util.List;

public interface TradeRecordService {

	List loadTradeData(List<String> account, String daysFilter, String fromDate, String toDate, String user);

}
