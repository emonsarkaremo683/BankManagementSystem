package com.elitetech_inc.ensarkbank.common.address.district.serviceimpl;


import com.elitetech_inc.ensarkbank.common.address.district.entity.District;
import com.elitetech_inc.ensarkbank.common.address.district.repository.DistrictRepository;
import com.elitetech_inc.ensarkbank.common.address.district.service.DistrictService;
import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import com.elitetech_inc.ensarkbank.common.address.division.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Override
    public District save(District district) {
        Division division = divisionRepository.findById(district.getDivision().getId())
                .orElseThrow(() -> new RuntimeException("Division not found"));

        district.setDivision(division);
        return districtRepository.save(district);
    }

    @Override
    public List<District> findAll() {
        return districtRepository.findAll();
    }

    @Override
    public List<District> findByDivisionId(Long id) {
        return districtRepository.findByDivisionId(id).stream().toList();
    }

    @Override
    public void delete(Long id) {
        districtRepository.deleteById(id);
    }

}
