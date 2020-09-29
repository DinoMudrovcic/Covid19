package com.dinomudrovcic.bisscovid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
}
