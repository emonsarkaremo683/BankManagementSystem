package com.elitetech_inc.ensarkbank.account_management.cheque_book.repository;

import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeaf;
import com.elitetech_inc.ensarkbank.common.enums.ChequeLeafStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChequeLeafRepository extends JpaRepository<ChequeLeaf, Long> {

    /** Returns all leaves belonging to a given cheque book. */
    List<ChequeLeaf> findByChequeBookId(Long chequeBookId);

    /** Finds a single leaf by its printed cheque number. */
    Optional<ChequeLeaf> findByChequeNumber(String chequeNumber);

    boolean existsByChequeNumber(String chequeNumber);

    /** Returns all leaves (any status) for a given cheque book. */
    List<ChequeLeaf> findByStatus(ChequeLeafStatus status);

    /**
     * Finds a leaf by its ID, but only if the given customer is a holder on the
     * leaf's parent account. Used for authorization checks.
     */
    @Query("SELECT cl FROM ChequeLeaf cl JOIN cl.chequeBook cb JOIN cb.account a JOIN a.holders h WHERE cl.id = :leafId AND h.customer.id = :customerId")
    Optional<ChequeLeaf> findByIdAndCustomerId(@Param("leafId") Long leafId, @Param("customerId") Long customerId);

    /**
     * Returns all cheque leaves belonging to a customer (across all their accounts)
     * filtered by a specific status. Use {@link #findByCustomerId(Long)} for an
     * unfiltered view.
     */
    @Query("SELECT cl FROM ChequeLeaf cl JOIN cl.chequeBook cb JOIN cb.account a JOIN a.holders h WHERE h.customer.id = :customerId AND cl.status = :status")
    List<ChequeLeaf> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") ChequeLeafStatus status);

    /**
     * Returns all cheque leaves belonging to a customer (across all their accounts),
     * regardless of status. Pass null or "ALL" to this from the service layer when
     * the caller does not want to filter by status.
     */
    @Query("SELECT cl FROM ChequeLeaf cl JOIN cl.chequeBook cb JOIN cb.account a JOIN a.holders h WHERE h.customer.id = :customerId")
    List<ChequeLeaf> findByCustomerId(@Param("customerId") Long customerId);

    /** Counts how many leaves in a cheque book have a given status. */
    @Query("SELECT COUNT(cl) FROM ChequeLeaf cl JOIN cl.chequeBook cb WHERE cb.id = :chequeBookId AND cl.status = :status")
    long countByChequeBookIdAndStatus(@Param("chequeBookId") Long chequeBookId, @Param("status") ChequeLeafStatus status);

    @Query("SELECT cl FROM ChequeLeaf cl WHERE cl.status = 'PRESENTED'")
    List<ChequeLeaf> findAllPresented();

    @Query("SELECT cl FROM ChequeLeaf cl JOIN cl.chequeBook cb JOIN cb.account a WHERE a.branch.id = :branchId AND cl.status = 'PRESENTED'")
    List<ChequeLeaf> findAllPresentedByBranchId(@Param("branchId") Long branchId);

    Optional<ChequeLeaf> findFirstByChequeBookIdAndStatusOrderByLeafNumberAsc(Long chequeBookId, ChequeLeafStatus status);

    Optional<ChequeLeaf> findByChequeNumberAndStatus(String chequeNumber, ChequeLeafStatus status);
}
