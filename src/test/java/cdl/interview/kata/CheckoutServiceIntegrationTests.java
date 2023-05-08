package cdl.interview.kata;

import cdl.interview.kata.exception.catalogue.NullItemCatalogueRepositoryException;
import cdl.interview.kata.exception.catalogue.NullPricingRuleCatalogueRepository;
import cdl.interview.kata.repository.ItemCatalogueRepository;
import cdl.interview.kata.repository.ItemCatalogueMapRepository;
import cdl.interview.kata.repository.PricingRuleCatalogueRepository;
import cdl.interview.kata.repository.PricingRuleCatalogueMapRepository;
import cdl.interview.kata.service.CheckoutService;
import cdl.interview.kata.service.CheckoutServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class CheckoutServiceIntegrationTests {
    private CheckoutService checkoutService;

    @BeforeEach
    public void setUp() {
        checkoutService = new CheckoutServiceImpl(ItemCatalogueRepository.getItemCatalogue(), PricingRuleCatalogueRepository.getPricingRuleCatalogue());
    }

    @Nested
    @DisplayName("Scan item tests")
    class ScanItemTests {
        @Test
        void scanItem_noItem_correctTotalPriceCalculated() {
            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(0.0));
        }

        @Test
        void scanItem_singleItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(50.0));
        }

        @Test
        void scanItem_multiItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('B');
            checkoutService.scanItem('C');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(100.0));
        }

        @Test
        void scanItem_multiItemSpecialPrice_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(130.0));
        }

        @Test
        void scanItem_multiItemAndSameSingleItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(180.0));
        }

        @Test
        void scanItem_multiItemAndDiffSingleItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('B');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(160.0));
        }

        @Test
        void scanItem_twoSetsOfMultiItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('B');
            checkoutService.scanItem('B');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(175.0));
        }

        @Test
        void scanItem_twoSetsOfMultiItemAndSingleItem_correctTotalPriceCalculated() {
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('A');
            checkoutService.scanItem('B');
            checkoutService.scanItem('B');
            checkoutService.scanItem('C');

            assertThat(checkoutService.getFinalPrice()).isEqualTo(BigDecimal.valueOf(195.0));
        }

        @Test
        void scanItem_multiItemAndNullPricingRuleCatalogue_exceptionThrown() {
            assertThatThrownBy(() -> {
                CheckoutService checkoutService1 = new CheckoutServiceImpl(ItemCatalogueRepository.getItemCatalogue(), null);
            }).isInstanceOf(NullPricingRuleCatalogueRepository.class)
                    .hasMessageContaining("Error: pricingRuleCatalogueRepository is null");
        }

        @Test
        void scanItem_multiItemAndNullItemCatalogue_exceptionThrown() {
            assertThatThrownBy(() -> {
                CheckoutService checkoutService1 = new CheckoutServiceImpl(null, PricingRuleCatalogueRepository.getPricingRuleCatalogue());
            }).isInstanceOf(NullItemCatalogueRepositoryException.class)
                    .hasMessageContaining("Error: itemCatalogueRepository is null");
        }

        @Test
        void scanItem_emptyItemCatalogue_priceCalculatedIsZero() {
            ItemCatalogueRepository itemCatalogueRepository = new ItemCatalogueMapRepository();
            CheckoutService checkoutService1 = new CheckoutServiceImpl(itemCatalogueRepository, PricingRuleCatalogueRepository.getPricingRuleCatalogue());

            checkoutService1.scanItem('A');

            assertThat(checkoutService1.getFinalPrice()).isEqualTo(BigDecimal.valueOf(0.0));
        }

        @Test
        void scanItem_emptyPricingRules_correctTotalPriceCalculatedWithNoDiscounts() {
            PricingRuleCatalogueRepository pricingRuleCatalogueRepository = new PricingRuleCatalogueMapRepository();
            CheckoutService checkoutService1 = new CheckoutServiceImpl(ItemCatalogueRepository.getItemCatalogue(), pricingRuleCatalogueRepository);

            checkoutService1.scanItem('A');
            checkoutService1.scanItem('A');
            checkoutService1.scanItem('A');

            assertThat(checkoutService1.getFinalPrice()).isEqualTo(BigDecimal.valueOf(150.0));
        }

        @Test
        void scanItem_validItem_printsCorrectSystemOutMessage() {
            PrintStream standardOut = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

            System.setOut(new PrintStream(outputStreamCaptor));
            checkoutService.scanItem('A');
            String outputResult = outputStreamCaptor.toString().trim();
            assertThat(outputResult).isEqualTo("Running Total = 50.0");
            System.setOut(standardOut);
        }

        @Test
        void scanItem_invalidItem_printsCorrectSystemOutMessage() {
            PrintStream standardOut = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

            System.setOut(new PrintStream(outputStreamCaptor));
            checkoutService.scanItem('Z');
            String outputResult = outputStreamCaptor.toString().trim();
            assertThat(outputResult).isEqualTo("Scanned item: Z does not exist in the item catalogue. Scanned item Z will be ignored.");
            System.setOut(standardOut);
        }
    }
}
