package com.elitetech_inc.ensarkbank.standing_order.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderRequest;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderResponse;
import com.elitetech_inc.ensarkbank.standing_order.entity.StandingOrder;
import com.elitetech_inc.ensarkbank.standing_order.mapper.StandingOrderMapper;
import com.elitetech_inc.ensarkbank.standing_order.repository.StandingOrderRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionService;
import com.elitetech_inc.ensarkbank.common.enums.StandingOrderFrequency;
import com.elitetech_inc.ensarkbank.common.enums.StandingOrderStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StandingOrderServiceImpl implements StandingOrderService {

    private final StandingOrderRepository standingOrderRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final StandingOrderMapper mapper;
    private final com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository transactionRepository;
    /**
     * Self-injected proxy so {@link #processOrder} is invoked through Spring's
     * transactional proxy (see {@link #processDueOrders}) instead of via a
     * plain {@code this.processOrder(...)} call, which would bypass the
     * {@code @Transactional(REQUIRES_NEW)} advice entirely. ObjectProvider,
     * not a @Lazy field: a field-level @Lazy on a Lombok-generated
     * constructor parameter does not reliably suppress Spring's
     * self-reference-is-a-cycle check (confirmed at runtime elsewhere in
     * this codebase).
     */
    private final ObjectProvider<StandingOrderServiceImpl> selfProvider;

    @Override
    @Transactional
    public StandingOrderResponse createStandingOrder(StandingOrderRequest request) {
        Account source = accountRepository.findById(request.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (source.getAvailableBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance in source account");
        }

        StandingOrder entity = mapper.toEntity(request);
        entity.setSourceAccount(source);
        entity.setNextExecutionDate(entity.getStartDate());
        entity.setStatus(StandingOrderStatus.ACTIVE);
        entity.setExecutionCount(0);

        StandingOrder saved = standingOrderRepository.save(entity);
        log.info("Standing order created: id={}, source={}, target={}, amount={}, frequency={}",
                saved.getId(), source.getAccountNumber(), saved.getTargetAccountNumber(),
                saved.getAmount(), saved.getFrequency());
        return mapper.toResponse(saved);
    }


    @Override
    @Transactional
    public StandingOrderResponse cancelStandingOrder(Long id) {
        StandingOrder order = standingOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standing order not found"));

        if (order.getStatus() == StandingOrderStatus.COMPLETED ||
                order.getStatus() == StandingOrderStatus.CANCELLED) {
            throw new IllegalStateException("Standing order is already " + order.getStatus());
        }

        order.setStatus(StandingOrderStatus.CANCELLED);
        StandingOrder saved = standingOrderRepository.save(order);
        log.info("Standing order cancelled: id={}", id);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public StandingOrderResponse pauseStandingOrder(Long id) {
        StandingOrder order = standingOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standing order not found"));

        if (order.getStatus() != StandingOrderStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE orders can be paused");
        }

        order.setStatus(StandingOrderStatus.PAUSED);
        StandingOrder saved = standingOrderRepository.save(order);
        log.info("Standing order paused: id={}", id);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public StandingOrderResponse resumeStandingOrder(Long id) {
        StandingOrder order = standingOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Standing order not found"));

        if (order.getStatus() != StandingOrderStatus.PAUSED) {
            throw new IllegalStateException("Only PAUSED orders can be resumed");
        }

        order.setStatus(StandingOrderStatus.ACTIVE);
        StandingOrder saved = standingOrderRepository.save(order);
        log.info("Standing order resumed: id={}", id);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StandingOrderResponse> getStandingOrder(Long id) {
        return standingOrderRepository.findById(id).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandingOrderResponse> getStandingOrdersByAccountId(Long accountId) {
        return standingOrderRepository.findBySourceAccountId(accountId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StandingOrderResponse> getActiveOrders() {
        return standingOrderRepository.findByStatus(StandingOrderStatus.ACTIVE)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deliberately NOT {@code @Transactional}. Each order is processed in
     * its own {@code REQUIRES_NEW} transaction via {@link #processOrder}, so
     * one order's {@code transactionService.createTransaction()} failure
     * can't mark a single shared transaction rollback-only and take every
     * other order in the batch down with it.
     */
    @Override
    public void processDueOrders() {
        List<StandingOrder> dueOrders = standingOrderRepository.findDueOrders(LocalDate.now());
        log.info("Processing {} due standing orders", dueOrders.size());

        for (StandingOrder order : dueOrders) {
            Long orderId = order.getId();
            try {
                selfProvider.getObject().processOrder(orderId);
            } catch (Exception e) {
                log.error("Failed to process standing order {}: {}", orderId, e.getMessage());
                selfProvider.getObject().markFailed(orderId);
            }
        }
    }

    /**
     * Persists the FAILED status in its own transaction. processOrder()'s
     * REQUIRES_NEW transaction has already rolled back by the time this
     * runs, so the order entity passed in from processDueOrders() may be
     * stale — reload it fresh rather than saving the in-memory instance.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(Long orderId) {
        standingOrderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(StandingOrderStatus.FAILED);
            standingOrderRepository.save(order);
        });
    }

    /**
     * Re-fetches the order by id rather than accepting the entity from
     * {@link #processDueOrders}: since that method is no longer
     * {@code @Transactional}, {@code findDueOrders()} runs its own
     * short-lived transaction and the returned entities are detached by the
     * time the loop reaches here — accessing the lazy {@code sourceAccount}
     * association on a detached entity would throw
     * {@code LazyInitializationException}. Loading fresh inside this
     * method's own transaction also means each attempt sees the latest
     * committed balance.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processOrder(Long orderId) {
        StandingOrder order = standingOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("StandingOrder", orderId));
        Account source = order.getSourceAccount();

        if (source.getAvailableBalance().compareTo(order.getAmount()) < 0) {
            log.warn("Insufficient balance for standing order {}: available={}, required={}",
                    order.getId(), source.getAvailableBalance(), order.getAmount());
            order.setStatus(StandingOrderStatus.FAILED);
            standingOrderRepository.save(order);
            return;
        }

        TransactionRequest request = new TransactionRequest();
        request.setAmount(order.getAmount());
        request.setRemarks("Standing order #" + order.getId() + " - " + order.getDescription());

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setChannel(TransactionChannel.SYSTEM);

        transactionService.createTransaction(request, transaction,
                source.getAccountNumber(), order.getTargetAccountNumber());

        order.setExecutionCount(order.getExecutionCount() + 1);
        order.setLastExecutionDate(LocalDate.now());

        if (order.getMaxExecutions() > 0 && order.getExecutionCount() >= order.getMaxExecutions()) {
            order.setStatus(StandingOrderStatus.COMPLETED);
        } else {
            order.setNextExecutionDate(calculateNextDate(order.getFrequency(), LocalDate.now()));
        }

        if (order.getEndDate() != null && LocalDate.now().isAfter(order.getEndDate())) {
            order.setStatus(StandingOrderStatus.COMPLETED);
        }

        standingOrderRepository.save(order);
        log.info("Standing order {} executed: amount={}, execution #{}", order.getId(), order.getAmount(), order.getExecutionCount());
    }

    private LocalDate calculateNextDate(StandingOrderFrequency frequency, LocalDate from) {
        return switch (frequency) {
            case DAILY -> from.plusDays(1);
            case WEEKLY -> from.plusWeeks(1);
            case BI_WEEKLY -> from.plusWeeks(2);
            case MONTHLY -> from.plusMonths(1);
            case QUARTERLY -> from.plusMonths(3);
            case YEARLY -> from.plusYears(1);
        };
    }

    @Override
    @Transactional
    public StandingOrderResponse updateStandingOrder(Long id, StandingOrderRequest request) {
        StandingOrder order = standingOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StandingOrder", id));
        order.setAmount(request.getAmount());
        order.setFrequency(request.getFrequency());
        order.setDescription(request.getDescription());
        order.setTargetAccountNumber(request.getTargetAccountNumber());
        if (request.getSourceAccountId() != null) {
            Account source = accountRepository.findById(request.getSourceAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", request.getSourceAccountId()));
            order.setSourceAccount(source);
        }
        return mapper.toResponse(standingOrderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse> getExecutionHistory(Long standingOrderId) {
        StandingOrder order = standingOrderRepository.findById(standingOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("StandingOrder", standingOrderId));
        String pattern = "Standing order #" + standingOrderId;
        return transactionRepository.findByRemarksContaining(pattern).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
