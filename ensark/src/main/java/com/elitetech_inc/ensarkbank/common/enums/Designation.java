package com.elitetech_inc.ensarkbank.common.enums;

import lombok.Getter;

@Getter
public enum Designation {

    CHIEF_EXECUTIVE_OFFICER("Chief Executive Officer", Role.SUPER_ADMIN),

    // Executive Management
    MANAGING_DIRECTOR("Managing Director", Role.SUPER_ADMIN),
    DEPUTY_MANAGING_DIRECTOR("Deputy Managing Director", Role.ADMIN),
    GENERAL_MANAGER("General Manager", Role.ADMIN),
    DEPUTY_GENERAL_MANAGER("Deputy General Manager", Role.ADMIN),
    ASSISTANT_GENERAL_MANAGER("Assistant General Manager", Role.ADMIN),

    // Branch Management
    BRANCH_MANAGER("Branch Manager", Role.BRANCH_MANAGER),
    ASSISTANT_BRANCH_MANAGER("Assistant Branch Manager", Role.BRANCH_MANAGER),
    OPERATIONS_MANAGER("Operations Manager", Role.BRANCH_MANAGER),

    // Banking Operations
    TELLER("Teller", Role.CASHIER),
    CASH_OFFICER("Cash Officer", Role.CASHIER),
    CUSTOMER_SERVICE_OFFICER("Customer Service Officer", Role.CUSTOMER_SERVICE),
    RELATIONSHIP_MANAGER("Relationship Manager", Role.CUSTOMER_SERVICE),
    LOAN_OFFICER("Loan Officer", Role.LOAN_OFFICER),
    ACCOUNTS_OFFICER("Accounts Officer", Role.ACCOUNTANT),
    COMPLIANCE_OFFICER("Compliance Officer", Role.AUDITOR),
    AUDIT_OFFICER("Audit Officer", Role.AUDITOR),

    // IT Department
    SYSTEM_ADMINISTRATOR("System Administrator", Role.ADMIN),
    SOFTWARE_ENGINEER("Software Engineer", Role.ADMIN),
    NETWORK_ENGINEER("Network Engineer", Role.ADMIN),
    DATABASE_ADMINISTRATOR("Database Administrator", Role.ADMIN),

    // HR & Administration
    HR_OFFICER("HR Officer", Role.ADMIN),
    ADMIN_OFFICER("Admin Officer", Role.ADMIN),

    // Finance
    FINANCE_OFFICER("Finance Officer", Role.ACCOUNTANT),
    TREASURY_OFFICER("Treasury Officer", Role.ACCOUNTANT),

    // Support
    SECURITY_OFFICER("Security Officer", Role.ADMIN),
    OFFICE_ASSISTANT("Office Assistant", Role.CUSTOMER_SERVICE),

    // Intern
    INTERN("Intern", Role.CUSTOMER_SERVICE);

    private final String displayName;
    private final Role defaultRole;

    Designation(String displayName, Role defaultRole) {
        this.displayName = displayName;
        this.defaultRole = defaultRole;
    }

    public static Designation fromDisplayName(String displayName) {
        for (Designation designation : values()) {
            if (designation.displayName.equalsIgnoreCase(displayName)) {
                return designation;
            }
        }
        throw new IllegalArgumentException("Unknown designation: " + displayName);
    }
}