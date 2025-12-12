package com.trade.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.utils.OutrightData;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {
	
	TradeDetails tradeDtoToSaveToDB(OutrightData currentRow);
	
	
	@Named("setNullIfEmptyString")
	static String setNullIfEmptyString(String value) {
		return value.isEmpty() ? null : value;
	}
}

