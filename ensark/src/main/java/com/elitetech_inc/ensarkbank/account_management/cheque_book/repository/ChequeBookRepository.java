package com.elitetech_inc.ensarkbank.account_management.cheque_book.repository;

import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeBook;
import com.elitetech_inc.ensarkbank.common.enums.ChequeBookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChequeBookRepository extends JpaRepository<ChequeBook, Long> {

    List<ChequeBook> findByAccountId(Long accountId);

    List<ChequeBook> findByStatus(ChequeBookStatus status);

    Optional<ChequeBook> findByBookSerialNumber(String bookSerialNumber);

    @Query("SELECT cb FROM ChequeBook cb JOIN cb.account a JOIN a.holders h WHERE cb.id = :chequeBookId AND h.customer.id = :customerId")
    Optional<ChequeBook> findByIdAndCustomerId(@Param("chequeBookId") Long chequeBookId, @Param("customerId") Long customerId);

    @Query("SELECT cb FROM ChequeBook cb JOIN cb.account a JOIN a.holders h WHERE h.customer.id = :customerId")
    List<ChequeBook> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(cb) > 0 FROM ChequeBook cb WHERE cb.id = :chequeBookId AND cb.status IN :statuses")
    boolean existsByIdAndStatusIn(@Param("chequeBookId") Long chequeBookId, @Param("statuses") List<ChequeBookStatus> statuses);

    @Query("SELECT cb FROM ChequeBook cb JOIN cb.account a WHERE a.branch.id = :branchId AND cb.status = :status")
    List<ChequeBook> findByBranchIdAndStatus(@Param("branchId") Long branchId, @Param("status") ChequeBookStatus status);

    @Query("SELECT cb FROM ChequeBook cb JOIN cb.account a JOIN a.holders h WHERE h.customer.user.email = :email")
    List<ChequeBook> findByCustomerEmail(@Param("email") String email);

    @Query("SELECT cb FROM ChequeBook cb WHERE cb.account.accountNumber = :accountNumber")
    List<ChequeBook> findByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT cb FROM ChequeBook cb WHERE " +
            "LOWER(cb.bookSerialNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(cb.account.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<ChequeBook> search(@Param("query") String query);

    @Query("SELECT cb FROM ChequeBook cb JOIN cb.account a WHERE cb.status = :status AND (:branchIds IS NULL OR a.branch.id IN :branchIds)")
    List<ChequeBook> findByStatusAndBranchIn(@Param("status") ChequeBookStatus status, @Param("branchIds") List<Long> branchIds);
}
