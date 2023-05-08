package cdl.interview.kata.repository;

import cdl.interview.kata.domain.ItemOffer;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of PricingRuleCatalogueRepository.
 */
public class PricingRuleCatalogueMapRepository implements PricingRuleCatalogueRepository {
    private final Map<Character, ItemOffer> rules = new HashMap<>();

    @Override
    public void addRule(char itemName, ItemOffer itemOffer) {
        rules.put(itemName, itemOffer);
    }

    @Override
    public ItemOffer getRule(char itemName) {
        return rules.get(itemName);
    }
}
