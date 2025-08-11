package com.project.code.Service;
//complete
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.ProductRepository;

public class ServiceClass {
    
// 1. **validateInventory Method**:
//    - Checks if an inventory record exists for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `boolean` (Returns `false` if inventory exists, otherwise `true`)
  public boolean validateInventory(Inventory inventory){
      if(inventory.getId()){
        return true;
    } else {
          return false;
      }
  }
// 2. **validateProduct Method**:
//    - Checks if a product exists by its name.
//    - Parameters: `Product product`
//    - Return Type: `boolean` (Returns `false` if a product with the same name exists, otherwise `true`)
public  boolean validateProduct(Product product){
            if(product.getName()){
                    return false;
            }
            else{
                return true;
            }
  }
// 3. **ValidateProductId Method**:
//    - Checks if a product exists by its ID.
//    - Parameters: `long id`
//    - Return Type: `boolean` (Returns `false` if the product does not exist with the given ID, otherwise `true`)
public boolean validateProductId(Product product,  long id){
      if(product.getId()==id){
          return true;
      } else{
          return false;
      }
}
// 4. **getInventoryId Method**:
//    - Fetches the inventory record for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `Inventory` (Returns the inventory record for the product-store combination)
    public Inventory getInventoryId(Inventory inventory){
            return inventory;
}
}