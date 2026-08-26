package com.elitetech_inc.ensarkbank.customer_management.customer_dashboard;

import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/state")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerDashboardController {

    private final CustomerDashboard customerDashboard;
    private final CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<CustomerDashboardResponse> getDashboard(Authentication auth) {
        String email = auth.getName();
        Customer customer = customerRepository.findByUserEmail(email)
                .orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerDashboard.toResponse(customer.getId()));
    }
}
