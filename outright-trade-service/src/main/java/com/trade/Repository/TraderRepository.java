package com.trade.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.trade.entity.TradeDetails;

@Repository
public interface TraderRepository extends JpaRepository<TradeDetails, String>, QuerydslPredicateExecutor<TradeDetails>{

	
}
