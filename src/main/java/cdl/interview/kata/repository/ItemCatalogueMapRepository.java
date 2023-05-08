package cdl.interview.kata.repository;

import cdl.interview.kata.domain.Item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of ItemCatalogueRepository.
 */
public class ItemCatalogueMapRepository implements ItemCatalogueRepository {
    private final Map<Character, BigDecimal> catalogue = new HashMap<>();

    @Override
    public void addItem(Item item) {
        catalogue.put(item.name(), item.unitPrice());
    }

    @Override
    public BigDecimal getItemPrice(char itemName) {
        return catalogue.get(itemName);
    }

    @Override
    public boolean itemExists(char itemName) {
        return catalogue.get(itemName) != null;
    }
}
