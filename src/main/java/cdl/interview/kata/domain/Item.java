package cdl.interview.kata.domain;

import java.math.BigDecimal;

/**
 * Item record to store immutable object state
 * <p>
 * Items are populated and retrieved via the ItemCatalogueRepository
 *
 * @param name
 * @param unitPrice
 */
public record Item(
        char name,
        BigDecimal unitPrice
) {
}
