package com.elitetech_inc.ensarkbank.human_resource_management.employee.service;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.EmployeeStatus;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.mapper.EmployeeMapper;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.request.EmployeeRequest;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.response.EmployeeResponse;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final RequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;

    @Override
    @Transactional
    public EmployeeResponse save(EmployeeRequest request, MultipartFile profilePicture) {
        requestValidator.validateEmployee(request);

        Employee employee = employeeMapper.toEmployee(request);

        if (profilePicture != null && !profilePicture.isEmpty()) {
            employee.setProfilePhoto(utils.uploadFile(profilePicture, "employee", request.getName()));
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", request.getBranchId()));
        employee.setBranch(branch);

        User user = employeeMapper.toUser(request);
        user.setActive(true);
        user.setEmailVerified(true);
        employee.setUser(userRepository.save(user));

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request, MultipartFile profilePicture) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setGender(request.getGender());
        existing.setDesignation(request.getDesignation());
        existing.setDob(request.getDob());
        existing.setPhoneNumber(request.getPhone());

        if (profilePicture != null && !profilePicture.isEmpty()) {
            existing.setProfilePhoto(utils.uploadFile(profilePicture, "employee", request.getName()));
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", request.getBranchId()));
        existing.setBranch(branch);

        User user = existing.getUser();
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setRole(request.getRole());
        userRepository.save(user);

        return employeeMapper.toResponse(employeeRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getByBranchId(Long branchId) {
        return employeeRepository.findEmployeeByBranchId(branchId)
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse findByEmployeeEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "email", email));
        return employeeMapper.toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateProfilePicture(Long id, MultipartFile profilePicture) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        if (profilePicture != null && !profilePicture.isEmpty()) {
            existing.setProfilePhoto(utils.uploadFile(profilePicture, "employee", existing.getName()));
        }

        return employeeMapper.toResponse(employeeRepository.save(existing));
    }

    @Override
    @Transactional
    public EmployeeResponse updateStatus(Long id, EmployeeStatus status) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        existing.setStatus(status);

        User user = existing.getUser();
        if (user != null) {
            user.setActive(status == EmployeeStatus.ACTIVE);
            userRepository.save(user);
        }

        return employeeMapper.toResponse(employeeRepository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return getAll();
        }
        return employeeRepository.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()) || e.getEmail().toLowerCase().contains(query.toLowerCase()))
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void resetPassword(Long id, String newPassword) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        User user = employee.getUser();
        if (user == null) {
            throw new com.elitetech_inc.ensarkbank.common.exception.BadRequestException("Employee has no linked user account");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public EmployeeResponse updateDesignation(Long id, com.elitetech_inc.ensarkbank.common.enums.Designation designation, com.elitetech_inc.ensarkbank.common.enums.Role role) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employee.setDesignation(designation);

        User user = employee.getUser();
        if (user != null) {
            user.setRole(role);
            userRepository.save(user);
        }

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }
}
