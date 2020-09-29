package com.dinomudrovcic.bisscovid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.Tests;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Long> {

}
