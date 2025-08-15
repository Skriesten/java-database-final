package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {
    // #1 Set up Controller
    // #2 Autowire Dependencies
@Autowired
private ProductRepository productRepository;
private InventoryRepository inventoryRepository;
private ServiceClass serviceClass;


    // #3 add Product method
    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        try {
            serviceClass.validateProduct(product);
            productRepository.save(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Error adding product: Data integrity violation", HttpStatus.CONFLICT); // Return 409 Conflict for data integrity issues
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // #4 Get Product by ID Method
    @GetMapping("/product/{id}")
    public  ResponseEntity<Product> getProductbyId(@PathVariable Long id){
        productRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return  new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK);
    }

    // #5 Update Product Method
@PutMapping
    public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
        productRepository.save(product);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
}

// #6 Filter by Category & Product method
      @GetMapping("/category/{name}/{category}")
    public ResponseEntity<Map<String, Object>> filterbyCategoryProduct(
          @PathVariable String name,
          @PathVariable String category) {
        ServiceClass  serviceClass = new ServiceClass();
        List<Product> filteredProducts = productRepository.findByCategory(category);
        Map<String, Object> response = new HashMap<>();
        response.put("products", filteredProducts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//  #7 List Product Method
    @GetMapping
    public ResponseEntity<List<Product>> listProduct(){
        HashMap<String , Object> productMap = new HashMap<>();
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

// #8.  The getProductBy Category and StoreID method
    // I hope this is right
@GetMapping("/filter/{category}/{storeId}")
public Map<String, Object> getProductByCategoryAndStoreId(@PathVariable String category, @PathVariable Long storeId) {
    // Get the  list of products from the category  in the Product class through the ProductRepository,
    // Filter the Inventory List in Product class by the storeId
    //  Return a Map of the filtered products
    List<Product> products= productRepository.findByCategory(category);
    List<Inventory>  inventories = inventoryRepository.findByStoreId(storeId);
    Map<String, Object> response = new HashMap<>();
    response.put("products", products);
    response.put("inventories", inventories);
    return response;
}

    // #9 Delete Product Method
@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Inventory inventory = new Inventory();
        boolean isValid =  serviceClass.validateInventory(inventory);
        if(isValid){
            inventoryRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found to delete", HttpStatus.NOT_FOUND);
        }
    }

 // 10. The `searchProduct` Method:
    @GetMapping("/searchProduct/{name}")
    public ResponseEntity<Map<String, Object>> searchProduct(@PathVariable String name){
             List<Product> products =    productRepository.findProductBySubName(name);
             Map<String, Object> response = new HashMap<>();
             response.put("products", products);
             return new ResponseEntity<>(response, HttpStatus.OK);
    }

} // ******  END OF CONTROLLER CLASS ************************

// ========== INSTRUCTIONS ================================

// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to designate it as a REST controller for handling HTTP requests.
//    - Map the class to the `/product` URL using `@RequestMapping("/product")`.

// 2. Autowired Dependencies:
//    - Inject the following dependencies via `@Autowired`:
//        - `ProductRepository` for CRUD operations on products.
//        - `ServiceClass` for product validation and business logic.
//        - `InventoryRepository` for managing the inventory linked to products.

// 3. Define the `addProduct` Method:
//    - Annotate with `@PostMapping` to handle POST requests for adding a new product.
//    - Accept `Product` object in the request body.
//    - Validate product existence using `validateProduct()` in `ServiceClass`.
//    - Save the valid product using `save()` method of `ProductRepository`.
//    - Catch exceptions (e.g., `DataIntegrityViolationException`) and return appropriate error message.

// 4. Define the `getProductbyId` Method:
//    - Annotate with `@GetMapping("/product/{id}")` to handle GET requests for retrieving a product by ID.
//    - Accept product ID via `@PathVariable`.
//    - Use `findById(id)` method from `ProductRepository` to fetch the product.
//    - Return the product in a `Map<String, Object>` with key `products`.

// 5. Define the `updateProduct` Method:
//    - Annotate with `@PutMapping` to handle PUT requests for updating an existing product.
//    - Accept updated `Product` object in the request body.
//    - Use `save()` method from `ProductRepository` to update the product.
//    - Return a success message with key `message` after updating the product.

// 6. Define the `filterbyCategoryProduct` Method:
//    - Annotate with `@GetMapping("/category/{name}/{category}")` to handle GET requests for filtering products by `name` and `category`.
//    - Use conditional filtering logic if `name` or `category` is `"null"`.
//    - Fetch products based on category using methods like `findByCategory()` or `findProductBySubNameAndCategory()`.
//    - Return filtered products in a `Map<String, Object>` with key `products`.

// 7. Define the `listProduct` Method:
//    - Annotate with `@GetMapping` to handle GET requests to fetch all products.
//    - Fetch all products using `findAll()` method from `ProductRepository`.
//    - Return all products in a `Map<String, Object>` with key `products`.

// 8. Define the `getProductbyCategoryAndStoreId` Method:
//    - Annotate with `@GetMapping("filter/{category}/{storeid}")` to filter products by `category` and `storeId`.
//    - Use `findProductByCategory()` method from `ProductRepository` to retrieve products.
//    - Return filtered products in a `Map<String, Object>` with key `product`.

// 9. Define the `deleteProduct` Method:
//    - Annotate with `@DeleteMapping("/{id}")` to handle DELETE requests for removing a product by its ID.
//    - Validate product existence using `ValidateProductId()` in `ServiceClass`.
//    - Remove product from `Inventory` first using `deleteByProductId(id)` in `InventoryRepository`.
//    - Remove product from `Product` using `deleteById(id)` in `ProductRepository`.
//    - Return a success message with key `message` indicating product deletion.

// 10. Define the `searchProduct` Method:
//    - Annotate with `@GetMapping("/searchProduct/{name}")` to search for products by `name`.
//    - Use `findProductBySubName()` method from `ProductRepository` to search products by name.
//    - Return search results in a `Map<String, Object>` with key `products`.