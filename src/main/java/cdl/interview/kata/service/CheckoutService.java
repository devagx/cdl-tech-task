package cdl.interview.kata.service;

import java.math.BigDecimal;

/**
 * This interface is used to provide a contract for any concrete implementations of CheckoutService.
 */
public interface CheckoutService {
    void scanItem(char name);

    BigDecimal getFinalPrice();
}
