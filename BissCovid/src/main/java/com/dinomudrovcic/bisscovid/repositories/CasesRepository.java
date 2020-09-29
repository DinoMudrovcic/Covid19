package com.dinomudrovcic.bisscovid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.Cases;

@Repository
public interface CasesRepository extends JpaRepository<Cases, Long> {

}
