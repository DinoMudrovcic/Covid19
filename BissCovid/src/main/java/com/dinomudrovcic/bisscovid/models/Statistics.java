package com.dinomudrovcic.bisscovid.models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statistics")
@Data
@NoArgsConstructor
public class Statistics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String continent;
	
	private String country;
	
	private String population;
	
	@OneToOne
	@JoinColumn(name = "cases_id", referencedColumnName = "id")
	private Cases cases;
	
	@OneToOne
	@JoinColumn(name = "deaths_id", referencedColumnName = "id")
	private Deaths deaths;
	
	@OneToOne
	@JoinColumn(name = "tests_id", referencedColumnName = "id")
	private Tests tests;
	
	private Date day;
	private Timestamp time;
	
}
