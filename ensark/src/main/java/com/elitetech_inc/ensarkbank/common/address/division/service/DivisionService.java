package com.elitetech_inc.ensarkbank.common.address.division.service;




import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DivisionService {

    Division save(Division division);
    List<Division> findAll();
    Optional<Division> findByDivisionId(Long id);
    void delete(Long id);


}
