package com.elitetech_inc.ensarkbank.human_resource_management.employee.service;

import com.elitetech_inc.ensarkbank.common.enums.EmployeeStatus;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.request.EmployeeRequest;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.response.EmployeeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse save(EmployeeRequest request, MultipartFile profilePicture);

    EmployeeResponse update(Long id, EmployeeRequest request, MultipartFile profilePicture);

    EmployeeResponse findById(Long id);

    List<EmployeeResponse> getAll();

    List<EmployeeResponse> getByBranchId(Long branchId);

    boolean existsByEmployeeEmail(String email);

    EmployeeResponse findByEmployeeEmail(String email);

    EmployeeResponse updateProfilePicture(Long id, MultipartFile profilePicture);

    EmployeeResponse updateStatus(Long id, EmployeeStatus status);

    void delete(Long id);

    List<EmployeeResponse> search(String query);

    void resetPassword(Long id, String newPassword);

    EmployeeResponse updateDesignation(Long id, com.elitetech_inc.ensarkbank.common.enums.Designation designation, com.elitetech_inc.ensarkbank.common.enums.Role role);
}
