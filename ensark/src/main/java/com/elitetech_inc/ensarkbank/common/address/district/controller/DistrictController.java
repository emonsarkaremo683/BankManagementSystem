package com.elitetech_inc.ensarkbank.common.address.district.controller;


import com.elitetech_inc.ensarkbank.common.address.district.entity.District;
import com.elitetech_inc.ensarkbank.common.address.district.service.DistrictService;
import com.elitetech_inc.ensarkbank.common.address.division.entity.Division;
import com.elitetech_inc.ensarkbank.common.address.division.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/district/")
public class DistrictController {

    @Autowired
    private DistrictService districtService;
    @Autowired
    private DivisionService divisionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<District> save(@RequestBody District d) {
        Division division = divisionService.findByDivisionId(d.getDivision().getId())
                .orElseThrow(
                        ()-> new RuntimeException("No data found")
                );
        return new ResponseEntity<>(districtService.save(d), HttpStatus.OK);
    }


    @GetMapping("all")
    public ResponseEntity<List<District>> findAll() {
        return new ResponseEntity<>(districtService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<District> update(@PathVariable Long id, @RequestBody District d) {
        d.setId(id);
        return new ResponseEntity<>(districtService.save(d), HttpStatus.OK);
    }


    @GetMapping("/division/{id}")
    public ResponseEntity<List<District>> findById(@PathVariable Long id){
        List<District> d = districtService.findByDivisionId(id).stream().toList();
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id){
        districtService.delete(id);
        return  new ResponseEntity<>("Data deleted", HttpStatus.OK);
    }


}