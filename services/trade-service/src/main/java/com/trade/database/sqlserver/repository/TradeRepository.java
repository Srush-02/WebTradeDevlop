package com.trade.database.sqlserver.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trade.database.sqlserver.entity.TradeDetails;



@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, String>{
	
	TradeDetails findBytraderUUID(String tradeUUIDsList);

	
}


