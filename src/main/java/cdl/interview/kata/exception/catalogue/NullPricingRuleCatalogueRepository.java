package cdl.interview.kata.exception.catalogue;

/**
 * Custom unchecked exception for handling errors where the NullPricingRuleCatalogueRepository is null
 */
public class NullPricingRuleCatalogueRepository extends RuntimeException {
    public NullPricingRuleCatalogueRepository(String errMessage) {
        super(errMessage);
    }
}

