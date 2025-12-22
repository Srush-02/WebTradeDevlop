package com.trade.database.sqlserver.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.database.sqlserver.entity.TradeDetails;



@Repository
public interface TradeRepository extends JpaRepository<TradeDetails, String>{
	
	TradeDetails findBytraderUUID(String tradeUUIDsList);
	
	@Query(
			  value = """
			    SELECT *
			    FROM Tarder_Data
			    WHERE buyer IN (:buyers)
AND TRY_CAST(created_ts AS BIGINT) BETWEEN :fromDate AND :toDate
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
					FROM Tarder_Data
					WHERE
					  (
					    CASE
					      WHEN TRY_CAST(created_ts AS BIGINT) IS NOT NULL
					        THEN DATEADD(MILLISECOND, TRY_CAST(created_ts AS BIGINT), '2025-01-01')
					      ELSE TRY_CONVERT(DATETIME2, created_ts, 126)
					    END
					  ) BETWEEN
  DATEADD(MILLISECOND, :fromDate, '2025-01-01')
  AND
  DATEADD(MILLISECOND, :toDate, '2026-01-01')

								  """,
			  nativeQuery = true
			)
			Collection<TradeDetails> findByCreatedTimestampBetween(
			    @Param("fromDate") long fromDate,
			    @Param("toDate") long toDate
			);


}


