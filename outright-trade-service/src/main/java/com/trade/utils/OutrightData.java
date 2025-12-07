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
	
	
//	public String getTradeUUID() {
//		return tradeUUID;
//	}
//	public void setTradeUUID(String tradeUUID) {
//		this.tradeUUID = tradeUUID;
//	}
//	public String getMetal() {
//		return metal;
//	}
//	public void setMetal(String metal) {
//		this.metal = metal;
//	}
//	public String getBuyer() {
//		return buyer;
//	}
//	public void setBuyer(String buyer) {
//		this.buyer = buyer;
//	}
//	public String getSeller() {
//		return seller;
//	}
//	public void setSeller(String seller) {
//		this.seller = seller;
//	}
//	public String getAmount() {
//		return amount;
//	}
//	public void setAmount(String amount) {
//		this.amount = amount;
//	}
//	public String getComments() {
//		return comments;
//	}
//	public void setComments(String comments) {
//		this.comments = comments;
//	}
//	public int getLots() {
//		return lots;
//	}
//	public void setLots(int lots) {
//		this.lots = lots;
//	}
//	public String getCreatedTimestamp() {
//		return createdTimestamp;
//	}
//	public void setCreatedTimestamp(String createdTimestamp) {
//		this.createdTimestamp = createdTimestamp;
//	}
//	
//	public double getPrice() {
//		return price;
//	}
//	
//	public void setPrice(double price) {
//		this.price = price;
//	}
//	
//	public String getStatus() {
//		return status;
//	}
//	
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	
//	public int getRowNumber() {
//		return rowNumber;
//	}
//	
//	public void setRowNumber(int rowNumber) {
//		this.rowNumber = rowNumber;
//	}
//
//
//	public String getPromptDate() {
//		return promptDate;
//	}
//
//	public void setPromptDate(String promptDate) {
//		this.promptDate = promptDate;
//	}
	 
}
