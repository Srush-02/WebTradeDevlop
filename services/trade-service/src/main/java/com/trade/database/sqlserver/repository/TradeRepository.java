package com.trade.database.sqlserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.trade.database.sqlserver.entity.TradeDetails;



@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, String>{
	
	
}


