package com.trade.database.sqlserver.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.trade.database.sqlserver.entity.TradeDetails;



@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, String>, QuerydslPredicateExecutor<TradeDetails>{
	
	TradeDetails findBytraderUUID(String tradeUUIDsList);
	
    List<TradeDetails> findByBuyerInAndCreatedTimestampBetweenOrderByCreatedTimestampDesc(List<String> buyers, long fromDate, String toDate);

    
    List<TradeDetails> findByBuyerInAndCreatedTimestampBetween(List<String> buyers, long fromDate, long toDate);


	
}


