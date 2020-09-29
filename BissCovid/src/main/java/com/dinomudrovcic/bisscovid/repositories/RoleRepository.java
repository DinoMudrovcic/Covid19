package com.dinomudrovcic.bisscovid.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dinomudrovcic.bisscovid.models.ERole;
import com.dinomudrovcic.bisscovid.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);
	
}
