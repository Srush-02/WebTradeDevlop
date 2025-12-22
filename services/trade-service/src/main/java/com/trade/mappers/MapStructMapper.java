package com.trade.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.utils.OutrightData;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {
	
	TradeDetails tradeDtoToSaveToDB(OutrightData currentRow);
	
	
	
	
	 
		OutrightData orderDetailsToOrderTraderBean(TradeDetails orderDetail);
	 List<OutrightData> tradeDetailListToreturnList(List<TradeDetails> orderDetailList);
	 
	 default String setNullIfEmptyString(String value) {
		    if (value == null || value.isEmpty()) {
		        return null;
		    }
		    return value;
		}
}

