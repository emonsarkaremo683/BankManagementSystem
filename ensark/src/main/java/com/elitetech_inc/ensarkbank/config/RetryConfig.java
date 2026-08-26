package com.elitetech_inc.ensarkbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Enables {@code @Retryable}/{@code @Recover} processing.
 *
 * <p>Used by {@link com.elitetech_inc.ensarkbank.accounting_system.ledger.service.LedgerPostingService}
 * to retry balance postings that lose a race against a concurrent update
 * (optimistic locking via {@code @Version}) instead of failing the whole
 * transaction on the first conflict.</p>
 */
@Configuration
@EnableRetry
public class RetryConfig {
}
