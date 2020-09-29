package com.dinomudrovcic.bisscovid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.Deaths;

@Repository
public interface DeathsRepository extends JpaRepository<Deaths, Long> {

}
