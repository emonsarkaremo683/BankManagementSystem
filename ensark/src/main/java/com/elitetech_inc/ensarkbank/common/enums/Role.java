package com.elitetech_inc.ensarkbank.common.enums;

public enum Role {
    SUPER_ADMIN,
    ADMIN,
    BRANCH_MANAGER,
    ACCOUNTANT,
    CASHIER,
    LOAN_OFFICER,
    CUSTOMER_SERVICE,
    ATM_MANAGER,
    AUDITOR,
    CUSTOMER;

    // Returns Spring Security compatible authority string
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
