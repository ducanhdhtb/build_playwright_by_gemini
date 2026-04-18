package com.tclife.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private Page page;

    // Locators
    private String pageTitle = ".title";
    private String addToCartButton = "button:has-text('Add to cart')";
    private String cartLink = ".shopping_cart_link";
    private String sortDropdown = ".product_sort_container";
    private String productPrice = ".inventory_item_price";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public String getInventoryPageTitle() {
        return page.textContent(pageTitle);
    }

    public void addProductToCart(String productName) {
        Allure.step("Adding product '" + productName + "' to cart", () -> {
            page.locator(".inventory_item")
                .filter(new Locator.FilterOptions().setHasText(productName))
                .locator(addToCartButton)
                .click();
        });
    }

    public void navigateToCart() {
        Allure.step("Navigating to the cart page", () -> {
            page.click(cartLink);
        });
    }

    public void sortProductsBy(String option) {
        Allure.step("Sorting products by: " + option, () -> {
            // The value for the option in the dropdown is different from the visible text
            String value;
            switch (option) {
                case "Price (low to high)":
                    value = "lohi";
                    break;
                case "Price (high to low)":
                    value = "hilo";
                    break;
                case "Name (A to Z)":
                    value = "az";
                    break;
                case "Name (Z to A)":
                    value = "za";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort option: " + option);
            }
            page.selectOption(sortDropdown, value);
        });
    }

    public List<Double> getProductPrices() {
        return page.locator(productPrice).allTextContents().stream()
                .map(price -> price.replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
