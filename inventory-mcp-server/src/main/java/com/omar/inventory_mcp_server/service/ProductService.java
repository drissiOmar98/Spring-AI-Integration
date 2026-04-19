package com.omar.inventory_mcp_server.service;


import com.omar.inventory_mcp_server.entity.Product;
import com.omar.inventory_mcp_server.repository.ProductRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer responsible for managing product inventory operations.
 *
 * <p>This service exposes MCP tools that allow AI models to interact
 * with the product database through natural language queries.
 *
 * <p>It provides functionalities such as:
 * retrieving products, searching by category, filtering by price,
 * and performing CRUD operations on products.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    /**
     * Retrieves all products from the inventory database.
     *
     * @return formatted string containing all products with their details
     */
    @McpTool(description = "Retrieves all products from the inventory database. " +
            "Returns a formatted list of all products with their details " +
            "including ID, name, category, price, and stock quantity.")
    public String getAllProducts() {
        List<Product> products = productRepository.findAll();

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products:\n\n", products.size()));

        for (Product product : products) {
            result.append(String.format("- %s (ID: %d)\n", product.getName(), product.getId()));
            result.append(String.format("  Category: %s\n", product.getCategory()));
            result.append(String.format("  Price: $%.2f\n", product.getPrice()));
            result.append(String.format("  Stock: %d units\n\n", product.getStock()));
        }

        return result.toString();
    }

    /**
     * Searches for products by category name.
     *
     * @param category the product category to search for
     * @return formatted list of matching products or empty message if none found
     */
    @McpTool(description = "Searches for products by category name. " +
            "Returns all products that match the specified category (case-sensitive). " +
            "Common categories include: Electronics, Books, Clothing, Appliances.")
    public String searchByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);

        if (products.isEmpty()) {
            return String.format("No products found in category '%s'.", category);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products in category '%s':\n\n",
                products.size(), category));

        for (Product product : products) {
            result.append(String.format("- %s (ID: %d) - $%.2f - Stock: %d\n",
                    product.getName(), product.getId(), product.getPrice(),
                    product.getStock()));
        }

        return result.toString();
    }

    /**
     * Finds all products with a price lower than the specified maximum price.
     *
     * @param maxPrice upper price limit
     * @return formatted list of products under the given price
     */
    @McpTool(description = "Finds all products priced below a specified maximum price. " +
            "Useful for finding budget-friendly options or products within a price range. " +
            "Price should be specified as a decimal number (e.g., 50.00).")
    public String findProductsUnderPrice(double maxPrice) {
        List<Product> products = productRepository.findByPriceLessThan(maxPrice);

        if (products.isEmpty()) {
            return String.format("No products found under $%.2f.", maxPrice);
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Found %d products under $%.2f:\n\n", products.size(), maxPrice));

        for (Product product : products) {
            result.append(String.format("- %s - $%.2f (%s) - Stock: %d\n",
                    product.getName(), product.getPrice(), product.getCategory(), product.getStock()));
        }

        return result.toString();
    }



}