package cdl.interview.kata;

import cdl.interview.kata.repository.ItemCatalogueRepository;
import cdl.interview.kata.repository.PricingRuleCatalogueRepository;
import cdl.interview.kata.service.CheckoutService;
import cdl.interview.kata.service.CheckoutServiceImpl;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * This class runs the main programme for the Checkout System. The programme will read input from the user,
 * calculate a running total and on an extra submission of the enter key will provide a final total price
 * which includes any discounts on entered products/items.
 */
public class CheckoutApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CheckoutService checkoutService = new CheckoutServiceImpl(ItemCatalogueRepository.getItemCatalogue(), PricingRuleCatalogueRepository.getPricingRuleCatalogue());

        System.out.println("Please enter items to scan");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println("Scanned item =  " + input);
            if (input.isEmpty()) {
                break;
            }
            char item = input.charAt(0);
            checkoutService.scanItem(item);
        }
        BigDecimal totalPrice = checkoutService.getFinalPrice();
        System.out.println("Total price =  " + totalPrice);
    }
}
