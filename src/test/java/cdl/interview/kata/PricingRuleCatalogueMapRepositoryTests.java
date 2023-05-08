package cdl.interview.kata;

import cdl.interview.kata.domain.ItemOffer;
import cdl.interview.kata.repository.PricingRuleCatalogueMapRepository;
import cdl.interview.kata.repository.PricingRuleCatalogueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PricingRuleCatalogueMapRepositoryTests {

    private PricingRuleCatalogueRepository pricingRuleCatalogueRepository;

    @BeforeEach
    public void setUp() {
        pricingRuleCatalogueRepository = new PricingRuleCatalogueMapRepository();
    }

    @Test
    void getRule_noRulesPopulated_returnsNull() {
        ItemOffer ruleResult = pricingRuleCatalogueRepository.getRule('Z');

        assertThat(ruleResult).isNull();
    }

    @Test
    void addRule_getRule_singleRule_returnsCorrectRuleValues() {
        pricingRuleCatalogueRepository.addRule('Z', new ItemOffer('Z', 3, BigDecimal.valueOf(50.0)));
        ItemOffer ruleResult = pricingRuleCatalogueRepository.getRule('Z');

        assertThat(ruleResult).isNotNull();
        assertThat(ruleResult.quantityInOffer()).isEqualTo(3);
        assertThat(ruleResult.offerPrice()).isEqualTo(BigDecimal.valueOf(50.0));
        assertThat(ruleResult.offerItem()).isEqualTo('Z');
    }

    @Test
    void addRule_getRule_multiRules_returnsCorrectRules() {
        pricingRuleCatalogueRepository.addRule('Z', new ItemOffer('Z', 3, BigDecimal.valueOf(50.0)));
        ItemOffer ruleResultZ = pricingRuleCatalogueRepository.getRule('Z');

        pricingRuleCatalogueRepository.addRule('Y', new ItemOffer('Y', 5, BigDecimal.valueOf(150.0)));
        ItemOffer ruleResultY = pricingRuleCatalogueRepository.getRule('Y');

        assertThat(ruleResultZ).isNotNull();
        assertThat(ruleResultZ.quantityInOffer()).isEqualTo(3);
        assertThat(ruleResultZ.offerPrice()).isEqualTo(BigDecimal.valueOf(50.0));
        assertThat(ruleResultZ.offerItem()).isEqualTo('Z');

        assertThat(ruleResultY).isNotNull();
        assertThat(ruleResultY.quantityInOffer()).isEqualTo(5);
        assertThat(ruleResultY.offerPrice()).isEqualTo(BigDecimal.valueOf(150.0));
        assertThat(ruleResultY.offerItem()).isEqualTo('Y');
    }
}
