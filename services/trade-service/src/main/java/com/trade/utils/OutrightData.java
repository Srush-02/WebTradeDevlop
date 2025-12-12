package com.trade.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutrightData {

	 private String tradeUUID;
	 
	 private String metal;
	 
	 private String buyer;
	 
	 private String seller;
	 
	 private String amount;
	 
	 private String comments;
	 
	 private int lots;
	 
	 private String createdTimestamp;
	 
	 private double price;
	 
	 private String status;
	 
	 private int rowNumber;
	 
	 
	 private String lastModifiedBy;
	 
	 private String createdBy;
	 
	private TradeMsgType tradeMsgType;
	 
	private enum TradeMsgType {
		 NEW,
        MODIFY,
        CANCEL
	}
   

       
	 
	@Override
	public String toString() {
		return "OutrightData [tradeUUID=" + tradeUUID + ", metal=" + metal + ", buyer=" + buyer + ", seller=" + seller
				+ ", amount=" + amount + ", comments=" + comments + ", lots=" + lots + ", createdTimestamp="
				+ createdTimestamp + ", price=" + price + ", status=" + status + ", rowNumber=" + rowNumber + ", lastModifiedBy=" + lastModifiedBy + "]";
	}
}
