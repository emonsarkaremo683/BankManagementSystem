package com.elitetech_inc.ensarkbank.common.address.district.repository;



import com.elitetech_inc.ensarkbank.common.address.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByDivisionId(Long DivisionId);
    Optional<District> findByName(String name);
}
