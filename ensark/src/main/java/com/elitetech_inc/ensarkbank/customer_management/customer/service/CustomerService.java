package com.elitetech_inc.ensarkbank.customer_management.customer.service;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.request.CustomerRequest;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.response.CustomerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request, MultipartFile profile, Map<DocumentType, MultipartFile> documents);

    CustomerResponse findById(Long id);

    List<CustomerResponse> getAll();

    List<CustomerResponse> getAllByBranchIds(List<Long> branchIds);

    boolean customerEmailExists(String email);

    List<CustomerResponse> search(String query);

    CustomerResponse updateByEmployee(Long id, CustomerRequest request, MultipartFile profile);

    CustomerResponse updatePassword(Long id, String oldPassword, String newPassword);

    CustomerResponse updateProfilePicture(Long id, MultipartFile profile);

    CustomerResponse findByEmail(String email);

    void delete(Long id);

    void deactivate(Long id);

    CustomerResponse findByPhone(String phone);

    boolean phoneExistsCheck(String phone);

    List<CustomerResponse> findByStatus(com.elitetech_inc.ensarkbank.common.enums.CustomerStatus status);
}
