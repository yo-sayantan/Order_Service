package com.orderingApp.orderingService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderingApp.orderingService.model.Product;
import com.orderingApp.orderingService.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	    @Autowired
	    private ProductService productService;

	    @GetMapping
	    public List<Product> getAllProducts() {
	        return productService.getAllProducts();
	    }

	    @PostMapping
	    public Product createProduct(@RequestBody Product product) {
	        return productService.createProduct(product);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
	        Product updatedProduct = productService.updateProduct(id, product);
	        if (updatedProduct == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(updatedProduct);
	    }

	    @PostMapping("/order/{id}")
	    public ResponseEntity<String> placeOrder(@PathVariable int id, @RequestParam int quantity) {
	        if (productService.checkStock(id, quantity)) {
	            productService.placeOrder(id, quantity);
	            return ResponseEntity.ok("Order placed successfully.");
	        }
	        return ResponseEntity.badRequest().body("Not enough stock.");
	    }

}
