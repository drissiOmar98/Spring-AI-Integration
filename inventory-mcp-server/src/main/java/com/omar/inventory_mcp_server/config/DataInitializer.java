package com.omar.inventory_mcp_server.config;



import com.omar.inventory_mcp_server.entity.Product;
import com.omar.inventory_mcp_server.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Laptop", "Electronics", 999.99, 15));
            repository.save(new Product("Wireless Mouse", "Electronics", 29.99, 50));
            repository.save(new Product("Mechanical Keyboard", "Electronics", 89.99, 30));
            repository.save(new Product("Spring in Action", "Books", 45.99, 25));
            repository.save(new Product("Clean Code", "Books", 39.99, 20));
            repository.save(new Product("T-Shirt", "Clothing", 19.99, 100));
            repository.save(new Product("Jeans", "Clothing", 59.99, 75));
            repository.save(new Product("Coffee Maker", "Appliances", 79.99, 40));
            repository.save(new Product("Blender", "Appliances", 49.99, 35));
            repository.save(new Product("Toaster", "Appliances", 29.99, 45));
        };
    }
}