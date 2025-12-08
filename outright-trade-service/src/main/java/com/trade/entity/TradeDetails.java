package com.trade.entity;

import java.beans.Transient;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
	
	@Entity
	@Data
	@NoArgsConstructor
	@Table(name = "DBS_Tarde")
	public class TradeDetails implements Persistable<String>{
		
		@Id
		@Column(name = "traderUUID")
		@JsonProperty("traderUUID")
		private String traderUUID;

		@Column(name = "buyer")
		@JsonProperty("buyer")
		private String buyer;
		

		@Column(name = "seller")
		@JsonProperty("seller")
		private String seller;


		@Column(name = "metal")
		@JsonProperty("metal")
		private String metal;

		@Column(name = "lots")
		@JsonProperty("lots")
		private int lots;

		@Column(name = "price")
		@JsonProperty("price")
		private double price;


		@Column(name = "prompt_date")
		@JsonProperty("prompt_date")
		private String promptDate;


		@Column(name = "created_ts")
		@JsonProperty("created_ts")
		@NotNull
		private String createdTimestamp;

		@Column(name = "last_modified_ts")
		@JsonProperty("last_modified_ts")
		private String lastModifiedTimestamp;

		@Column(name = "last_modified_by")
		@JsonProperty("last_modified_by")
		private String lastModifiedBy;


		@Column(name = "created_by")
		@JsonProperty("created_by")
		private String createdBy;

		
		@Column(name = "comment")
		@JsonProperty("comment")
		private String comment;

		
		//@Transient
		private boolean isSave = true;
		
		//TODO houseTrade
		
		public boolean isSave() {
			return isSave;
		}

		@Override
		public String getId() {
			return null;
		}

		@Override
		public boolean isNew() {
			return false;
		}

}
