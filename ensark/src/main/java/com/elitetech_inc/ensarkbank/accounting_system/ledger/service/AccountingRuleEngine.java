package com.elitetech_inc.ensarkbank.accounting_system.ledger.service;

import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.common.enums.BalanceEffect;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

/**
 * Central accounting rule engine that determines balance effects
 * based on EntryType and AccountCategory.
 *
 * <p>Uses an {@link EnumMap} for O(1) lookup — no switch statements,
 * no if-else chains. The rules follow standard double-entry accounting
 * principles (IAS/IFRS):</p>
 *
 * <table>
 *   <tr><th>Account Category</th><th>DEBIT</th><th>CREDIT</th></tr>
 *   <tr><td>ASSET</td><td>INCREASE</td><td>DECREASE</td></tr>
 *   <tr><td>LIABILITY</td><td>DECREASE</td><td>INCREASE</td></tr>
 *   <tr><td>EQUITY</td><td>DECREASE</td><td>INCREASE</td></tr>
 *   <tr><td>INCOME</td><td>DECREASE</td><td>INCREASE</td></tr>
 *   <tr><td>EXPENSE</td><td>INCREASE</td><td>DECREASE</td></tr>
 * </table>
 *
 * <p>This engine is immutable and stateless. It is thread-safe.</p>
 */
@Component
public class AccountingRuleEngine {

    private final Map<EntryType, Map<AccountCategory, BalanceEffect>> rules;

    public AccountingRuleEngine() {
        this.rules = buildRules();
    }

    /**
     * Resolves the balance effect for a given entry type and account category.
     *
     * @param entryType      DEBIT or CREDIT
     * @param accountCategory ASSET, LIABILITY, EQUITY, INCOME, or EXPENSE
     * @return the balance effect (INCREASE or DECREASE)
     * @throws IllegalArgumentException if the combination is unknown
     */
    public BalanceEffect resolve(EntryType entryType, AccountCategory accountCategory) {
        Map<AccountCategory, BalanceEffect> categoryMap = rules.get(entryType);
        if (categoryMap == null) {
            throw new IllegalArgumentException("Unknown entry type: " + entryType);
        }
        BalanceEffect effect = categoryMap.get(accountCategory);
        if (effect == null) {
            throw new IllegalArgumentException(
                    "No rule for EntryType=" + entryType + " + AccountCategory=" + accountCategory
            );
        }
        return effect;
    }

    /**
     * Calculates the new balance after applying a journal entry.
     *
     * @param currentBalance the current account balance
     * @param entryType      DEBIT or CREDIT
     * @param accountCategory the account's category
     * @param amount         the transaction amount (must be positive)
     * @return the new balance after applying the entry
     */
    public BigDecimal calculateNewBalance(
            BigDecimal currentBalance,
            EntryType entryType,
            AccountCategory accountCategory,
            BigDecimal amount
    ) {
        if (currentBalance == null) {
            currentBalance = BigDecimal.ZERO;
        }
        BalanceEffect effect = resolve(entryType, accountCategory);
        return switch (effect) {
            case INCREASE -> currentBalance.add(amount);
            case DECREASE -> currentBalance.subtract(amount);
        };
    }

    /**
     * Builds the immutable rule table.
     */
    private Map<EntryType, Map<AccountCategory, BalanceEffect>> buildRules() {
        Map<EntryType, Map<AccountCategory, BalanceEffect>> rules = new EnumMap<>(EntryType.class);

        // DEBIT rules
        Map<AccountCategory, BalanceEffect> debitRules = new EnumMap<>(AccountCategory.class);
        debitRules.put(AccountCategory.ASSET, BalanceEffect.INCREASE);
        debitRules.put(AccountCategory.LIABILITY, BalanceEffect.DECREASE);
        debitRules.put(AccountCategory.EQUITY, BalanceEffect.DECREASE);
        debitRules.put(AccountCategory.INCOME, BalanceEffect.DECREASE);
        debitRules.put(AccountCategory.EXPENSE, BalanceEffect.INCREASE);
        rules.put(EntryType.DEBIT, debitRules);

        // CREDIT rules
        Map<AccountCategory, BalanceEffect> creditRules = new EnumMap<>(AccountCategory.class);
        creditRules.put(AccountCategory.ASSET, BalanceEffect.DECREASE);
        creditRules.put(AccountCategory.LIABILITY, BalanceEffect.INCREASE);
        creditRules.put(AccountCategory.EQUITY, BalanceEffect.INCREASE);
        creditRules.put(AccountCategory.INCOME, BalanceEffect.INCREASE);
        creditRules.put(AccountCategory.EXPENSE, BalanceEffect.DECREASE);
        rules.put(EntryType.CREDIT, creditRules);

        return rules;
    }
}
