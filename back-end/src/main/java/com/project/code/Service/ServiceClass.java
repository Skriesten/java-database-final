package com.project.code.Service;
//complete
import com.project.code.Model.Inventory;
import com.project.code.Model.OrderItem;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.StoreRepository;

import java.util.Optional;

public class ServiceClass {
    private ProductRepository productRepository;
    private StoreRepository storeRepository;
    private InventoryRepository inventoryRepository;

    // It's good practice to ensure the Product and Store within the Inventory object
    // are managed (i.e., retrieved from the database or newly persisted)
    // rather than transient objects passed directly from a request.
    // This prevents potential issues with detached entities.
    // Check if an inventory record already exists for this product and store
    // Using existsByProductAndStore for efficiency if only existence is needed

    public boolean validateInventory(Inventory inventory, OrderItem orderItem) {
        long product = productRepository.getProductById(inventory.getProduct().getId()).getId(); // Retrieve product
        Store store = storeRepository.findById(inventory.getStore().getId());       // Retrieve store
        Inventory exists =  inventoryRepository.findByProductIdandStoreId(product, store); // verify if the item isin the store.
        if(exists==null){
        return false;
        }else {
            return true;
        }
    }

public  boolean validateProduct(Product product){
            if(Boolean.parseBoolean(product.getName())){
                    return false;
            }
            else{
                return true;
            }
  }

public boolean validateProductId( Long id){
      Optional<Product> productId = productRepository.findById(id);
        if(productId.isPresent()){
          return true;
      } else{
          return false;
      }
}

 public Inventory getInventoryId(Inventory inventory){
            return inventory;
}

} // ******* END OF CLASS  ***************

// =========  INSTRUCTIONS

// 1. **validateInventory Method**:
//    - Checks if an inventory record exists for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `boolean` (Returns `false` if inventory exists, otherwise `true`)

// 2. **validateProduct Method**:
//    - Checks if a product exists by its name.
//    - Parameters: `Product product`
//    - Return Type: `boolean` (Returns `false` if a product with the same name exists, otherwise `true`)

// 3. **ValidateProductId Method**:
//    - Checks if a product exists by its ID.
//    - Parameters: `long id`
//    - Return Type: `boolean` (Returns `false` if the product does not exist with the given ID, otherwise `true`)

//4. **getInventoryId Method**:
//    - Fetches the inventory record for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `Inventory` (Returns the inventory record for the product-store combination)
