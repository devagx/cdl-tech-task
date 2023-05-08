package cdl.interview.kata.repository;

import cdl.interview.kata.domain.Item;

import java.math.BigDecimal;

/**
 * This interface is used to provide a contract for any concrete implementations of ItemCatalogueRepository.
 */
public interface ItemCatalogueRepository {
    static ItemCatalogueRepository getItemCatalogue() {
        //Assumption is lowercase or uppercase, both are valid when querying the data source
        ItemCatalogueRepository itemCatalogueRepository = new ItemCatalogueMapRepository();
        itemCatalogueRepository.addItem(new Item('A', BigDecimal.valueOf(50.0)));
        itemCatalogueRepository.addItem(new Item('B', BigDecimal.valueOf(30.0)));
        itemCatalogueRepository.addItem(new Item('C', BigDecimal.valueOf(20.0)));
        itemCatalogueRepository.addItem(new Item('D', BigDecimal.valueOf(15.0)));

        return itemCatalogueRepository;
    }

    void addItem(Item item);

    BigDecimal getItemPrice(char itemName);

    boolean itemExists(char itemName);
}
