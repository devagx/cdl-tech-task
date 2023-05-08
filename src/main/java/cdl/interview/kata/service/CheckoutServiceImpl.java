package cdl.interview.kata.service;

import cdl.interview.kata.exception.catalogue.NullItemCatalogueRepositoryException;
import cdl.interview.kata.exception.catalogue.NullPricingRuleCatalogueRepository;
import cdl.interview.kata.repository.ItemCatalogueRepository;
import cdl.interview.kata.domain.ItemOffer;
import cdl.interview.kata.repository.PricingRuleCatalogueRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of CheckoutService.
 */
public class CheckoutServiceImpl implements CheckoutService {
    private final Map<Character, Integer> scannedItems = new HashMap<>();
    private final ItemCatalogueRepository itemCatalogueRepository;
    private final PricingRuleCatalogueRepository pricingRuleCatalogueRepository;

    public CheckoutServiceImpl(ItemCatalogueRepository itemCatalogueRepository, PricingRuleCatalogueRepository pricingRuleCatalogueRepository) {
        if (itemCatalogueRepository == null) {
            throw new NullItemCatalogueRepositoryException("Error: itemCatalogueRepository is null");
        } else if (pricingRuleCatalogueRepository == null) {
            throw new NullPricingRuleCatalogueRepository("Error: pricingRuleCatalogueRepository is null");
        }

        this.itemCatalogueRepository = itemCatalogueRepository;
        this.pricingRuleCatalogueRepository = pricingRuleCatalogueRepository;
    }

    @Override
    public void scanItem(char itemName) {
        itemName = Character.toUpperCase(itemName);
        if (itemCatalogueRepository.itemExists(itemName)) {
            scannedItems.merge(itemName, 1, Integer::sum);

            BigDecimal finalPrice = getFinalPrice();

            System.out.println(String.format("Running Total = %s", finalPrice));
        } else {
            System.out.println(String.format("Scanned item: %s does not exist in the item catalogue. Scanned item %s will be ignored.", itemName, itemName));
        }
    }

    @Override
    public BigDecimal getFinalPrice() {
        BigDecimal finalPrice = new BigDecimal("0.0");

        for (Map.Entry<Character, Integer> scannedItem : scannedItems.entrySet()) {
            int scannedItemQuantity = scannedItem.getValue();
            char itemName = Character.toUpperCase(scannedItem.getKey());
            BigDecimal scannedItemUnitPrice = itemCatalogueRepository.getItemPrice(itemName);

            ItemOffer itemOffer = pricingRuleCatalogueRepository.getRule(itemName);

            if (itemOffer != null && (itemOffer.quantityInOffer() <= scannedItemQuantity)) {
                //Calculate offer prices
                finalPrice = finalPrice.add(calculateItemOffers(scannedItemQuantity, scannedItemUnitPrice, itemOffer));
            } else {
                //Calculate normal prices
                finalPrice = finalPrice.add(scannedItemUnitPrice.multiply(BigDecimal.valueOf(scannedItemQuantity)));
            }

        }
        return finalPrice;
    }

    private BigDecimal calculateItemOffers(int scannedItemQuantity, BigDecimal scannedItemUnitPrice, ItemOffer itemOffer) {
        BigDecimal remainderPrice = scannedItemUnitPrice.multiply(BigDecimal.valueOf((scannedItemQuantity % itemOffer.quantityInOffer())));
        BigDecimal offerPrice = itemOffer.offerPrice().multiply(BigDecimal.valueOf(scannedItemQuantity / itemOffer.quantityInOffer()));

        return remainderPrice.add(offerPrice);
    }
}
