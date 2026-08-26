package com.elitetech_inc.ensarkbank.human_resource_management.employee.repository;

import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeeByBranchId(Long branchId);

    Optional<Employee> findEmployeeByUser_Id(Long userId);

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.branch.id IN :branchIds")
    long countByBranchIds(@Param("branchIds") List<Long> branchIds);
}
