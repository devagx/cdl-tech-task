package cdl.interview.kata;

import cdl.interview.kata.domain.Item;
import cdl.interview.kata.repository.ItemCatalogueMapRepository;
import cdl.interview.kata.repository.ItemCatalogueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ItemCatalogueMapRepositoryTests {

    private ItemCatalogueRepository itemCatalogueRepository;

    @BeforeEach
    public void setUp() {
        itemCatalogueRepository = new ItemCatalogueMapRepository();
    }

    @Test
    void getItemPrice_noItemsPopulated_returnsNull() {

        BigDecimal unitPriceResult = itemCatalogueRepository.getItemPrice('Z');

        assertThat(unitPriceResult).isNull();
    }

    @Test
    void addItem_getItemPrice_singleItem_returnsCorrectPrice() {
        itemCatalogueRepository.addItem(new Item('Z', BigDecimal.valueOf(50.0)));
        BigDecimal unitPriceResult = itemCatalogueRepository.getItemPrice('Z');

        assertThat(unitPriceResult)
                .isNotNull()
                .isEqualTo(BigDecimal.valueOf(50.0));
    }

    @Test
    void addItem_getItemPrice_multiItems_returnsCorrectPrices() {
        itemCatalogueRepository.addItem(new Item('Z', BigDecimal.valueOf(50.0)));
        itemCatalogueRepository.addItem(new Item('Y', BigDecimal.valueOf(150.0)));

        BigDecimal unitPriceResultZ = itemCatalogueRepository.getItemPrice('Z');
        BigDecimal unitPriceResultY = itemCatalogueRepository.getItemPrice('Y');

        assertThat(unitPriceResultZ)
                .isNotNull()
                .isEqualTo(BigDecimal.valueOf(50.0));

        assertThat(unitPriceResultY)
                .isNotNull()
                .isEqualTo(BigDecimal.valueOf(150.0));
    }
}
