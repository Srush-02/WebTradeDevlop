package com.trade.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;


public class DateHandler implements AttributeConverter <Long,String > {
	
		@Override
		public Long convertToEntityAttribute(String dbData) {

			return (dbData == null) ? 0 : ZonedDateTime.of(Timestamp.valueOf(dbData).toLocalDateTime(), ZoneId.of("UTC")).toInstant().toEpochMilli();

		}

		@Override
		public String convertToDatabaseColumn(Long attribute) {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(attribute),ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")) ;
		
		}
	
}
