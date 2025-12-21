package com.trade.database.sqlserver.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.database.sqlserver.entity.TradeDetails;



@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, String>{
	
	TradeDetails findBytraderUUID(String tradeUUIDsList);
	
	@Query(
			  value = """
			    SELECT *
			    FROM trade_details
			    WHERE buyer IN (:buyers)
			      AND CAST(created_timestamp AS BIGINT)
			          BETWEEN :fromDate AND :toDate
			  """,
			  nativeQuery = true
			)
			List<TradeDetails> findTrades(
			    @Param("buyers") List<String> buyers,
			    @Param("fromDate") long fromDate,
			    @Param("toDate") long toDate
			);
	

	@Query(
			  value = """
			    SELECT *
			    FROM trade_details
			    WHERE CAST(created_timestamp AS BIGINT)
			          BETWEEN :fromDate AND :toDate
			  """,
			  nativeQuery = true
			)
			Collection<TradeDetails> findByCreatedTimestampBetween( @Param("fromDate") long fromDate, @Param("toDate") long toDates);


	
}


