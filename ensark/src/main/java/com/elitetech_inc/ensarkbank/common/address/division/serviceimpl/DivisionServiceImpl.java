package com.elitetech_inc.ensarkbank.common.address.division.serviceimpl;



import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import com.elitetech_inc.ensarkbank.common.address.division.repository.DivisionRepository;
import com.elitetech_inc.ensarkbank.common.address.division.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;


    @Override
    public Division save(Division division) {

        return divisionRepository.save(division);
    }

    @Override
    public List<Division> findAll() {
        return divisionRepository.findAll();
    }

    @Override
    public Optional<Division> findByDivisionId(Long id) {
        return divisionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        divisionRepository.deleteById(id);

    }

}
