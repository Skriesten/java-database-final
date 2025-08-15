package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController
{

// 2. Autowired Dependencies: ============================
    @Autowired
  private  ProductRepository productRepository;
   private InventoryRepository inventoryRepository;
   private ServiceClass serviceClass;

    // 3.  The `updateInventory` Method: ================
    @PostMapping("update")
    public ProductRepository updateInventory(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        Product product = new Product();
        Store store = new Store();
        if ((productRepository.getProductById(product.getId()) != null) && (inventoryRepository.findByStoreId(store.getId()) != null)) {
            return (ProductRepository) ResponseEntity.ok().build();
        } else {
            return (ProductRepository) ResponseEntity.notFound();
        }
    }

// 4. The `saveInventory` Method: ====================
    @PostMapping("/update") // Handles POST requests to /api/inventory
    public ResponseEntity<Map<String, Object>> saveInventory(@Valid @RequestBody Inventory inventory) {
        Map<String, Object> response = new HashMap<>();
        try {
            ServiceClass serviceClass = new ServiceClass();
            // 1. Validate if the inventory already exists
            boolean isValidToSave = serviceClass.validateInventory(inventory);
            if (!isValidToSave) {
                // If inventory already exists, return a message stating so
                response.put("message", "Inventory record for this product and store already exists.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
            } else {
                // 2. If it doesn't exist, save the inventory
                ResponseEntity<Map<String, Object>> savedInventory = saveInventory(inventory);
                response.put("message", "Inventory saved successfully.");
                response.put("inventory", savedInventory); // Optionally return the saved inventory details
                return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
            }
        } catch (IllegalArgumentException e) {
            // Catch exceptions from the service layer, like if product or store are invalid
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    // 5.  The `getAllProducts` Method: ===================
    @GetMapping("/{storeId}/products")
    public ResponseEntity<Map<String, Object>> getAllProducts(@PathVariable Long storeId){
       Map<String, Object> results = new HashMap<>();
      // ServiceClass serviceClass = new ServiceClass();
       Inventory inventory = new Inventory();
        try {
            List<Product> products = Collections.singletonList(inventory.getProduct()); // Retrieve products
            results.put("products", inventory.getProduct());
            return new ResponseEntity<>(results, HttpStatus.OK); // Return 200 OK with products
        } catch (GlobalExceptionHandler.StoreNotFoundException e) { // Catch the custom exception if the store doesn't exist
            results.put("message", e.getMessage());
            return new ResponseEntity<>(results, HttpStatus.NOT_FOUND); // Return 404 Not Found
        } catch (Exception e) {
            results.put("message", "An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(results, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 Internal Server Error
        }
    }

    // 6. The `getProductName` Method: =========================
    @GetMapping("/filter/category/{category}/name/{name}")
    public ResponseEntity<Map<String, Object>> getProductName(
          @PathVariable String category,
          @PathVariable String name) {
        ProductRepository productRepository = null;
        Map<String, Object> response = new HashMap<>();
        try {
            List<Inventory> filteredProducts = productRepository.findByNameandCategory(name, category);
            response.put("product", filteredProducts); // Key is "product" as requested
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 7. The `searchProduct` Method:  ===========================
    //  I couldn't figure this oune out.
//    @GetMapping("/search") // Specific path for this GET request
//    public ResponseEntity<Map<String, List<Product>>> searchProduct(
//          @RequestParam("name") String name, // Binds the "name" query parameter to this String variable
//          @RequestParam("storeId") Long storeId) { // Binds the "storeId" query parameter to this Long variable
        // Call your service to perform the product search
        //Store store = new Store();
        //ProductRepository productRepository = null;
       // ServiceClass serviceClass = new ServiceClass();

        // Get the Product name based upon the productId in the Inventory class
//        Inventory inventory = new Inventory();
//        String productName = inventory.getProduct().getId().;
//       // InventoryRepository inventoryRepository = null;
//        List<Inventory> filteredProducts = new ArrayList<>();
//        //filteredProducts.get(productRepository.getProductById())
//        Product product = new Product();
//        Long newProductId = inventoryRepository.findByProduct(product.getId());
//        inventory.getProduct().setName(productName);
//        inventoryRepository.findByProductIdandStoreId(productName, storeId );
//        List<Product> products = Collections.singletonList(productRepository.findByName(name));
//
//        Map<String, List<Product>> response = new HashMap<>();
//        response.put("product", products); // Package the results under the key "product"
//        return ResponseEntity.ok(response); // Return a 200 OK response with the product list
//    }

    // 8. The `removeProduct` Method:  ========================
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable  Long productId){
        ProductRepository productRepository = null;
        InventoryRepository inventoryRepository = null;
        if( productRepository.findById(productId) == null){
            return ResponseEntity.notFound().build();
        } else
        {
            productRepository.deleteById(productId);
            inventoryRepository.deleteByProductId(productId);
            return ResponseEntity.ok().build();
        }
    }

    // 9. The `validateQuantity` Method:  ========================
    // Couldn't figure this one out.
    @GetMapping("/totals")
    public boolean validateQuantity(Product productId, Store storeId) {
        ServiceClass serviceClass = new ServiceClass();
//serviceClass.validateInventory();{
        return false;
    }

}  // ***********  END OF INVENTORY CONTROLLER CLASS  ***********

//  ============  INSTRUCTIONS =============================
// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to indicate that this is a REST controller, which handles HTTP requests and responses.
//    - Use `@RequestMapping("/inventory")` to set the base URL path for all methods in this controller. All endpoints related to inventory will be prefixed with `/inventory`.

// 2. Autowired Dependencies:
//    - Autowire necessary repositories and services:
//      - `ProductRepository` will be used to interact with product data (i.e., finding, updating products).
//      - `InventoryRepository` will handle CRUD operations related to the inventory.
//      - `ServiceClass` will help with the validation logic (e.g., validating product IDs and inventory data).

// 3. Define the `updateInventory` Method:
//    - This method handles HTTP PUT requests to update inventory for a product.
//    - It takes a `CombinedRequest` (containing `Product` and `Inventory`) in the request body.
//    - The product ID is validated, and if valid, the inventory is updated in the database.
//    - If the inventory exists, update it and return a success message. If not, return a message indicating no data available.

// 4. Define the `saveInventory` Method:
//    - This method handles HTTP POST requests to save a new inventory entry.
//    - It accepts an `Inventory` object in the request body.
//    - It first validates whether the inventory already exists. If it exists, it returns a message stating so. If it doesn’t exist, it saves the inventory and returns a success message.

// 5. Define the `getAllProducts` Method:
//    - This method handles HTTP GET requests to retrieve products for a specific store.
//    - It uses the `storeId` as a path variable and fetches the list of products from the database for the given store.
//    - The products are returned in a `Map` with the key `"products"`.

// 6. Define the `getProductName` Method:
//    - This method handles HTTP GET requests to filter products by category and name.
//    - If either the category or name is `"null"`, adjust the filtering logic accordingly.
//    - Return the filtered products in the response with the key `"product"`.

// 7. Define the `searchProduct` Method:
//    - This method handles HTTP GET requests to search for products by name within a specific store.
//    - It uses `name` and `storeId` as parameters and searches for products that match the `name` in the specified store.
//    - The search results are returned in the response with the key `"product"`.

// 8. Define the `removeProduct` Method:
//    - This method handles HTTP DELETE requests to delete a product by its ID.
//    - It first validates if the product exists. If it does, it deletes the product from the `ProductRepository` and also removes the related inventory entry from the `InventoryRepository`.
//    - Returns a success message with the key `"message"` indicating successful deletion.

// 9. Define the `validateQuantity` Method:
//    - This method handles HTTP GET requests to validate if a specified quantity of a product is available in stock for a given store.
//    - It checks the inventory for the product in the specified store and compares it to the requested quantity.
//    - If sufficient stock is available, return `true`; otherwise, return `false`.