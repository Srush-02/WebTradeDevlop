package com.trade.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutrightData {

	 private String traderUUID;
	 
	 private String metal;
	 
	 private String buyer;
	 
	 private String seller;
	 
	 private String comment;
	 
	 private int lots;
	 
	 private String createdTimestamp;
	 
	 private double price;
	 
	 private String tradeType;
	 
	 private int rowNumber;
	 
	 private String lastModifiedTimestamp;
	 
	 private String lastModifiedBy;
	 
	 private String createdBy;
	 
	private String traderStatus;
	
	private String tradeDate;

	@Override
	public String toString() {
		return "OutrightData [traderUUID=" + traderUUID + ", metal=" + metal + ", buyer=" + buyer + ", seller=" + seller
				+ ", comment=" + comment + ", lots=" + lots + ", createdTimestamp="
				+ createdTimestamp + ", price=" + price + ", tradeType=" + tradeType + ", rowNumber=" + rowNumber
				+ ", lastModifiedTimestamp=" + lastModifiedTimestamp + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdBy=" + createdBy + ", traderStatus=" + traderStatus + ", tradeDate=" + tradeDate + "]";
	}
	 
	 
	
}
