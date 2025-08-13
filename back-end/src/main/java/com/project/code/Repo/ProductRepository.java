package com.project.code.Repo;
// Completed

import com.project.code.Model.OrderDetails;
import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
// #2
    public List<Product> findAll();

// #3
    public List<Product> findByCategory(String category);

// #4
    public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

// #5
    public Product findBySku(String sku);

// #6
    public Product findByName(String name);

// #7
    public List<Product> findByNameLike(long storeId, String pname);

// #8  This was added to assist with one of the methods in the OrderService class
   public Product getProductById(long id);
}
// 1. Add the repository interface:
//    - Extend JpaRepository<Product, Long> to inherit basic CRUD functionality.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.
// Example: public interface ProductRepository extends JpaRepository<Product, Long> {}

// 2. Add custom query methods:
//    - **findAll**:
//      - This method will retrieve all products.
//      - Return type: List<Product>
// Example: public List<Product> findAll();

//    3. - **findByCategory**:
//      - This method will retrieve products by their category.
//      - Return type: List<Product>
//      - Parameter: String category
// Example: public List<Product> findByCategory(String category);

//   4.  - **findByPriceBetween**:
//      - This method will retrieve products within a price range.
//      - Return type: List<Product>
//      - Parameters: Double minPrice, Double maxPrice
// Example: public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

//    5. - **findBySku**:
//      - This method will retrieve a product by its SKU.
//      - Return type: Product
//      - Parameter: String sku
// Example: public Product findBySku(String sku);

//    6.- **findByName**:
//      - This method will retrieve a product by its name.
//      - Return type: Product
//      - Parameter: String name
// Example: public Product findByName(String name);

//    7. - **findByNameLike**:
//      - This method will retrieve products by a name pattern for a specific store.
//      - Return type: List<Product>
//      - Parameters: Long storeId, String pname
//      - Use @Query annotation to write a custom query.

