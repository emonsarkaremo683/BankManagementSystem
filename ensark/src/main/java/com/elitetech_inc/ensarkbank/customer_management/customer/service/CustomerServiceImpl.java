package com.elitetech_inc.ensarkbank.customer_management.customer.service;

import com.elitetech_inc.ensarkbank.auth_management.auth.security.EmailConfig;
import com.elitetech_inc.ensarkbank.auth_management.auth.security.JwtUtil;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import com.elitetech_inc.ensarkbank.common.address.address.dto.request.AddressRequest;
import com.elitetech_inc.ensarkbank.common.address.address.entity.Address;
import com.elitetech_inc.ensarkbank.common.enums.AddressType;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.mapper.CustomerMapper;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.request.CustomerRequest;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.response.CustomerResponse;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import com.elitetech_inc.ensarkbank.customer_management.kyc.repository.KycRepository;
import com.elitetech_inc.ensarkbank.util.EmailUtil;
import com.elitetech_inc.ensarkbank.util.NotificationUtil;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    private final KycRepository kycRepository;
    private final RequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;
    private final JwtUtil jwtUtil;
    private final EmailConfig emailConfig;
    private final NotificationUtil notificationUtil;
    private final EmailUtil emailUtil;

    @Override
    @Transactional
    public CustomerResponse create(CustomerRequest request, MultipartFile profile, Map<DocumentType, MultipartFile> documents) {
        requestValidator.validateCustomer(request);
        if (customerRepository.existsByUserEmail(request.getEmail())){
            throw new RuntimeException("Already registered");
        }
        Customer customer = customerMapper.toCustomer(request);

        if (profile != null && !profile.isEmpty()) {
            customer.setProfile(utils.uploadFile(profile, "customer", request.getName()));
        }

        User user = customerMapper.toUser(request);
        resolveAddresses(request, user);
        customer.setUser(userRepository.save(user));

        Customer savedCustomer = customerRepository.save(customer);

        Kyc kyc = createKycWithDocuments(savedCustomer, documents);
        Kyc savedKyc = kycRepository.save(kyc);
        savedCustomer.setKyc(savedKyc);

        sendVerificationEmail(user, request.getName());
        notifyAuthorities(savedCustomer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllByBranchIds(List<Long> branchIds) {
        return customerRepository.findCustomersByBranchIds(branchIds)
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean customerEmailExists(String email) {
        return customerRepository.existsByUserEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> search(String query) {
        return customerRepository.searchCustomers(query)
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findByEmail(String email) {
        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse updateByEmployee(Long id, CustomerRequest request, MultipartFile profile) {
        requestValidator.validateCustomer(request);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));

        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setGender(request.getGender());
        customer.setOccupation(request.getOccupation());
        customer.setDob(request.getDob());

        if (profile != null && !profile.isEmpty()) {
            customer.setProfile(utils.uploadFile(profile, "customer", request.getName()));
        }

        User user = customer.getUser();
        user.setEmail(request.getEmail());

        if (request.getAddresses() != null) {
            for (AddressRequest a : request.getAddresses()) {
                Address address = customerMapper.toAddress(a);
                user.addAddress(address);
            }
        }

        userRepository.save(user);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public CustomerResponse updatePassword(Long id, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));

        User user = customer.getUser();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse updateProfilePicture(Long id, MultipartFile profile) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));

        if (customer.getProfile() != null) {
            utils.deleteFile("customer", customer.getProfile());
        }

        if (profile != null && !profile.isEmpty()) {
            customer.setProfile(utils.uploadFile(profile, "customer", customer.getName()));
        }

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    private void resolveAddresses(CustomerRequest request, User user) {
        if (request.getAddresses() == null) return;

        for (AddressRequest a : request.getAddresses()) {
            Address address = customerMapper.toAddress(a);
            user.addAddress(address);
        }
    }

    private Kyc createKycWithDocuments(Customer customer, Map<DocumentType, MultipartFile> documents) {
        Kyc kyc = new Kyc();
        kyc.setStatus(KYCStatus.PENDING);
        kyc.setCustomer(customer);

        if (documents != null && !documents.isEmpty()) {
            for (Map.Entry<DocumentType, MultipartFile> entry : documents.entrySet()) {
                DocumentType docType = entry.getKey();
                MultipartFile docFile = entry.getValue();

                if (docFile == null || docFile.isEmpty()) continue;

                String filePath = utils.uploadFile(docFile, "kyc", docType.name());
                KycDocuments kycDoc = new KycDocuments();
                kycDoc.setDoc_type(docType);
                kycDoc.setPath("kyc/" + filePath);
                kycDoc.setKyc(kyc);
                kyc.getDocuments().add(kycDoc);
            }
        }

        return kyc;
    }

    private void sendVerificationEmail(User user, String name) {
        try {
            String token = jwtUtil.generateVerificationToken(user.getEmail());
            emailConfig.sendVerificationEmail(user.getEmail(), name, token);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
    }

    private void notifyAuthorities(Customer customer) {
        notificationUtil.notifyAuthorities(
                NotificationType.CUSTOMER_REGISTERED,
                "New Customer Registered",
                "Customer " + customer.getName() + " has been registered. KYC status: PENDING.",
                String.valueOf(customer.getId()),
                "CUSTOMER"
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
        customer.setStatus(com.elitetech_inc.ensarkbank.common.enums.CustomerStatus.INACTIVE);
        if (customer.getUser() != null) {
            customer.getUser().setActive(false);
            userRepository.save(customer.getUser());
        }
        customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "phone", phone));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean phoneExistsCheck(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findByStatus(com.elitetech_inc.ensarkbank.common.enums.CustomerStatus status) {
        return customerRepository.findAll().stream()
                .filter(c -> c.getStatus() == status)
                .map(customerMapper::toResponse)
                .toList();
    }
}
