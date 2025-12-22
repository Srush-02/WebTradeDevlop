package com.trade.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trade.database.sqlserver.entity.TradeDetails;
import com.trade.database.sqlserver.repository.TradeRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ValidateTrade {
	
	private TradeRepository repo;

	public void finalCommonDetailValidation(OutrightData msgData, ObjectNode rowErrors, String userName) {
		
		int lots = msgData.getLots();
		
		String buyer = msgData.getBuyer();
		
		String seller  = msgData.getSeller();
		
		if(buyer.equals("")) {
			setErrorObj(AppConstant.BUYER_FIELD, AppConstant.INVALID_BUYER, rowErrors);
		}

		if(seller.equals("")) {
			setErrorObj(AppConstant.SELLER_FIELD, AppConstant.INVALID_SELLER, rowErrors);
		}

		if (buyer.equalsIgnoreCase(seller)) {
			setErrorObj(AppConstant.SELLER_FIELD, AppConstant.DIFF_BUY_SELLER, rowErrors);
		}
		
		
		if (msgData.getComment() != null && !msgData.getComment().toString().matches("^.{1,128}$|^$")) {
			setErrorObj("comments", AppConstant.INVALID_COMMENTS, rowErrors);
		}
		
		if (lots <= 0 || lots >= 10000) {
			setErrorObj("lots", AppConstant.INVALID_LOTS, rowErrors);
		}
		
		String price = Double.toString(msgData.getPrice());

		if (!price.matches("(^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$)")){ //Regex
			setErrorObj("price", AppConstant.INVALID_PRICE, rowErrors);
		}

		if(Math.abs(msgData.getPrice()) > 10000) {
			setErrorObj("price", "Invalid price range", rowErrors);
		}
	}
	
	
	private void setErrorObj(String label, String message, ObjectNode rowErrors) {
		rowErrors.put(label, message);

	}
	
	
	public String checkLastModifiedTimeValid(String tradeUUID, long eventEpochTime, String user) {

		String validOrder = "";
		
		TradeDetails tradeResponse = repo.findBytraderUUID(tradeUUID);

		if (tradeResponse == null || tradeResponse.getLastModifiedTimestamp().isEmpty()) {
			validOrder = tradeUUID + ":" +AppConstant.TRADER_ERROR;
			return validOrder;
		}
		return validOrder;
	}

	
}