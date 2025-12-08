package com.trade.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	public static final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

}
