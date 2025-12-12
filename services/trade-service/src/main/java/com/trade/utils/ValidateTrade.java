package com.trade.utils;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import tools.jackson.databind.node.ObjectNode;

@Component
@AllArgsConstructor
public class ValidateTrade {

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
		
		
		if (msgData.getComments() != null && !msgData.getComments().toString().matches("^.{1,128}$|^$")) {
			setErrorObj("comments", AppConstant.INVALID_COMMENTS, rowErrors);
		}
		
		if (lots <= 0 || lots >= 10000) {
			setErrorObj("lots", AppConstant.INVALID_LOTS, rowErrors);
		}
		
		String price = Double.toString(msgData.getPrice());

		if (!price.matches("(^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$)")){
			setErrorObj("price", AppConstant.INVALID_PRICE, rowErrors);
		}

		if(Math.abs(msgData.getPrice()) > 10000) {
			setErrorObj("price", "Invalid price range", rowErrors);
		}
	}
	
	
	private void setErrorObj(String label, String message, ObjectNode rowErrors) {
		rowErrors.put(label, message);

	}
	
}