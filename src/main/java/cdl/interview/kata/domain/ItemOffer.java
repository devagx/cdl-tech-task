package cdl.interview.kata.domain;

import java.math.BigDecimal;

/**
 * ItemOffer record to store immutable object state
 * <p>
 * ItemsOffer are populated and retrieved via the PriceRuleRepository and retrieved in the CheckoutService
 *
 * @param offerItem
 * @param quantityInOffer
 * @param offerPrice
 */
public record ItemOffer(
        char offerItem,
        int quantityInOffer,
        BigDecimal offerPrice

) {
}
