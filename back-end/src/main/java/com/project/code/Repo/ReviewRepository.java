package com.project.code.Repo;
// Completed
import com.project.code.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, String> {

public List<Review> findByStoreIdAndProductId(long storeId,long productId);

    // 1. Add the repository interface:
//    - Extend MongoRepository<Review, String> to inherit basic CRUD functionality for MongoDB operations.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.
// Example: public interface ReviewRepository extends MongoRepository<Review, String> {}

// 2. Add custom query methods:
//    - **findByStoreIdAndProductId**:
//      - This method will retrieve reviews for a specific product and store.
//      - Return type: List<Review>
//      - Parameters: Long storeId, Long productId
// Example: public List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}
