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



}