package cdl.interview.kata.repository;

import cdl.interview.kata.domain.ItemOffer;

import java.math.BigDecimal;

/**
 * This interface is used to provide a contract for any concrete implementations of PricingRuleCatalogueRepository.
 */
public interface PricingRuleCatalogueRepository {
    static PricingRuleCatalogueRepository getPricingRuleCatalogue() {
        //Assumption is lowercase or uppercase, both are valid when querying the data source
        PricingRuleCatalogueRepository pricingRuleCatalogueRepository = new PricingRuleCatalogueMapRepository();
        pricingRuleCatalogueRepository.addRule('A', new ItemOffer('A', 3, BigDecimal.valueOf(130.0)));
        pricingRuleCatalogueRepository.addRule('B', new ItemOffer('B', 2, BigDecimal.valueOf(45.0)));

        return pricingRuleCatalogueRepository;
    }

    void addRule(char itemName, ItemOffer itemOffer);

    ItemOffer getRule(char itemName);
}
