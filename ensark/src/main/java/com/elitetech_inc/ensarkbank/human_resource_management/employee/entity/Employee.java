package com.elitetech_inc.ensarkbank.human_resource_management.employee.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.Designation;
import com.elitetech_inc.ensarkbank.common.enums.EmployeeStatus;
import com.elitetech_inc.ensarkbank.common.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@Data
public class Employee extends BaseEntity {

    private String name;

    @Column(unique = true,  nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    @Column(unique = true)
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    private String profilePhoto;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
