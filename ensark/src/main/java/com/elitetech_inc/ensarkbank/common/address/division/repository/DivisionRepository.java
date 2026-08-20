package com.elitetech_inc.ensarkbank.common.address.division.repository;



import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findByName(String name);
}
