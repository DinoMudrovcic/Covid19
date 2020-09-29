package com.dinomudrovcic.bisscovid.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.Statistics;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
	
	@Query(value = "SELECT * FROM statistics "
			+ "WHERE country = :countryName AND day BETWEEN :dateFrom AND :dateTo "
			+ "ORDER BY country ASC",
			nativeQuery = true)
	List<Statistics> findStatisticsByCountryAndDate(@Param("countryName") String countryName,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
