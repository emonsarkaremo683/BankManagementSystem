package com.elitetech_inc.ensarkbank.common.address.policestation.serviceimpl;


import com.elitetech_inc.ensarkbank.common.address.district.repository.DistrictRepository;
import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.address.policestation.repository.PoliceStationRepository;
import com.elitetech_inc.ensarkbank.common.address.policestation.service.PoliceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class PoliceStationServiceImpl implements PoliceStationService {

    @Autowired
    private PoliceStationRepository policeStationRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public PoliceStation save(PoliceStation ps) {

        return policeStationRepository.save(ps);

    }


    @Override
    public List<PoliceStation> getAll() {

        return policeStationRepository.findAll();
    }

    @Override
    public Optional<PoliceStation> findById(Long id) {
        return policeStationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        policeStationRepository.deleteById(id);
    }

    @Override
    public List<PoliceStation> findByDistrictId(Long districtId) {
        return policeStationRepository.findByDistrictId(districtId);
    }

}
