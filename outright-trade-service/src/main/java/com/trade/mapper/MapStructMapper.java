package com.trade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.trade.entity.TradeDetails;
import com.trade.utils.OutrightData;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
	
	TradeDetails tradeDtoToSaveToDB(OutrightData currentRow);
	
	
	@Named("setNullIfEmptyString")
	static String setNullIfEmptyString(String value) {
		return value.isEmpty() ? null : value;
	}

}
