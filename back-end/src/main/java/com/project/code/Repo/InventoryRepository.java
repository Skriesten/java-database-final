package com.project.code.Repo;
// Completed
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    // #2  Find by Product and Store
    @Query
    public Inventory findByProductIdandStoreId(long productId, Store storeId);

    // #3 Find by Store ID
    public List<Inventory> findByStoreId(long storeId);

    // #4  Delete by Product ID
    @Modifying
    @Transactional
    public void deleteByProductId(long productId);

    Store store(Store store);

    public List<Inventory> findByProductId(Product id);

    int getStockLevel(Product productId);

// ============= INSTRUCTIONS ====================================== //

// 1. Add the repository interface:
//    - Extend JpaRepository<Inventory, Long> to inherit basic CRUD functionality.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.
// Example: public interface InventoryRepository extends JpaRepository<Inventory, Long> {}

// 2. Add custom query methods:
//    - **findByProductIdandStoreId**:
//      - This method will allow you to find an inventory record by its product ID and store ID.
//      - Return type: Inventory
//      - Parameters: Long productId, Long storeId
// Example: public Inventory findByProductIdandStoreId(Long productId, Long storeId);


// 3.     - **findByStore_Id**:
//      - This method will allow you to find a list of inventory records for a specific store.
//      - Return type: List<Inventory>
//      - Parameter: Long storeId
// Example: public List<Inventory> findByStore_Id(Long storeId);

//  4.    - **deleteByProductId**:
//      - This method will allow you to delete all inventory records related to a specific product ID.
//      - Return type: void
//      - Parameter: Long productId
//      - Use @Modifying and @Transactional annotations to ensure the database is modified correctly.
}