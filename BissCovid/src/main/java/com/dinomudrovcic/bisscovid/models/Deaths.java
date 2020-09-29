package com.dinomudrovcic.bisscovid.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "deaths")
@Data
public class Deaths {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("new")
	private String newDeaths;
	@JsonProperty("1M_pop")
	private String oneMPopulation;
	@JsonProperty("total")
	private String totalDeaths;
	
}
