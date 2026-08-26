package com.elitetech_inc.ensarkbank.common.enums;

/**
 * Represents the effect of a journal entry on an account balance.
 *
 * <p>This enum abstracts the directional impact of DEBIT/CREDIT entries
 * based on the account's {@link AccountCategory}, following standard
 * double-entry accounting rules (IAS/IFRS).</p>
 *
 * <h3>Accounting Rules:</h3>
 * <ul>
 *   <li><b>ASSET</b>: Debit=INCREASE, Credit=DECREASE</li>
 *   <li><b>LIABILITY</b>: Debit=DECREASE, Credit=INCREASE</li>
 *   <li><b>EQUITY</b>: Debit=DECREASE, Credit=INCREASE</li>
 *   <li><b>INCOME</b>: Debit=DECREASE, Credit=INCREASE</li>
 *   <li><b>EXPENSE</b>: Debit=INCREASE, Credit=DECREASE</li>
 * </ul>
 */
public enum BalanceEffect {

    INCREASE,
    DECREASE
}
