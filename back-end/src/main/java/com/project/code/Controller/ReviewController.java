package com.project.code.Controller;


import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/reviews")
public class ReviewController {
// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to designate it as a REST controller for handling HTTP requests.
//    - Map the class to the `/reviews` URL using `@RequestMapping("/reviews")`.
@Autowired
    ReviewRepository reviewRepository;
    CustomerRepository customerRepository;


 // 2. Autowired Dependencies:
//    - Inject the following dependencies via `@Autowired`:
//        - `ReviewRepository` for accessing review data.
//        - `CustomerRepository` for retrieving customer details associated with reviews.


    @GetMapping("/{storeId/{productId}}")
    public ResponseEntity<Map<String, Object>> getReviews(@PathVariable Long storeId, @PathVariable Long productId){

        Map<String, Object> response = new HashMap<>();

        try {
            List<Review> filteredReviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
            response.put("reviews", filteredReviews);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler.StoreNotFoundException e) { // Assuming you create ProductNotFoundException
            response.put("message", "Store not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            response.put("message", "An unexpected error occurred while fetching reviews: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
// 3. Define the `getReviews` Method:
//    - Annotate with `@GetMapping("/{storeId}/{productId}")` to fetch reviews for a specific product in a store by `storeId` and `productId`.
//    - Accept `storeId` and `productId` via `@PathVariable`.
//    - Fetch reviews using `findByStoreIdAndProductId()` method from `ReviewRepository`.
//    - Filter reviews to include only `comment`, `rating`, and the `customerName` associated with the review.
//    - Use `findById(review.getCustomerId())` from `CustomerRepository` to get customer name.
//    - Return filtered reviews in a `Map<String, Object>` with key `reviews`.

    
   

