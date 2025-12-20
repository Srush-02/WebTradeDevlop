package com.trade.database.sqlserver.entity;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Tarder_Data", schema = "dbo")
public class TradeDetails implements Persistable<String>{
	
	@Id
	@Column(name = "traderUUID")
	@JsonProperty("traderUUID")
	private String traderUUID;

	@Column(name = "buyer")
	@JsonProperty("buyer")
	@NotNull
	private String buyer;
	

	@Column(name = "seller")
	@JsonProperty("seller")
	@NotNull
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
	
	@Transient
	private boolean isSave = true;


	@Column(name = "created_ts")
	@JsonProperty("created_ts")
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
	
	
	@Column(name = "traderStatus")
	@JsonProperty("traderStatus")
	private String traderStatus;
	
	
	@Column(name = "tradeType")
	@JsonProperty("tradeType")
	private String tradeType;
	

	public boolean isSave() {
		return isSave;
	}
	
	@Override
	public String getId() {
	 return this.traderUUID;
	}

	@Override
	public boolean isNew() {
		return this.traderUUID == null;
	}

}