package com.trade.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.AttributeConverter;

public class DateTimeToStringConverter implements AttributeConverter<String, Timestamp> {
	@Override
	public Timestamp convertToDatabaseColumn(String attribute) {
		return (attribute != null && !attribute.equals(""))
				? Timestamp.valueOf(LocalDateTime.parse(attribute, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")))
				: null;
	}

	@Override
	public String convertToEntityAttribute(Timestamp dbData) {
		return dbData != null ? String.valueOf(dbData.getTime()) : null;
	}
}
