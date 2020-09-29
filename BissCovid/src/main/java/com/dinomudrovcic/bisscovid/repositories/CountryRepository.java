package com.dinomudrovcic.bisscovid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>  {

}
