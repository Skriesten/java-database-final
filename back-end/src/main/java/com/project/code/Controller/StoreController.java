package com.project.code.Controller;

import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/store")
public class StoreController {

// #2 Auto wire dependencies
@Autowired
StoreRepository storeRepository;
OrderService orderService;

    // 3. The `addStore` Method:
@PostMapping
    public ResponseEntity<String> createStore(@RequestBody Store store){
    storeRepository.save(store);
    return ResponseEntity.ok().build();
    }

    // 4. The `validateStore` Method:
    @GetMapping("/{storeId}")
    public boolean validateStore(@PathVariable Long storeId){
        return storeRepository.existsById(storeId);
    }

    // 5. The `placeOrder` Method:
@PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody Order order){
            PlaceOrderRequestDTO placeOrderRequestDTO = new PlaceOrderRequestDTO();
              if(order == null) {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order was not placed successfully");
              } else {
                  orderService.saveOrder(placeOrderRequestDTO);
                  return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully");
              }
    }

} // ********* END OF CLASS *****************************

//  ======  INSTRUCTIONS =================================

// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to designate it as a REST controller for handling HTTP requests.
//    - Map the class to the `/store` URL using `@RequestMapping("/store")`.

// 2. Autowired Dependencies:
//    - Inject the following dependencies via `@Autowired`:
//        - `StoreRepository` for managing store data.
//        - `OrderService` for handling order-related functionality.

// 3. Define the `addStore` Method:
//    - Annotate with `@PostMapping` to create an endpoint for adding a new store.
//    - Accept `Store` object in the request body.
//    - Return a success message in a `Map<String, String>` with the key `message` containing store creation confirmation.

// 4. Define the `validateStore` Method:
//    - Annotate with `@GetMapping("validate/{storeId}")` to check if a store exists by its `storeId`.
//    - Return a **boolean** indicating if the store exists.

// 5. Define the `placeOrder` Method:
//    - Annotate with `@PostMapping("/placeOrder")` to handle order placement.
//    - Accept `PlaceOrderRequestDTO` in the request body.
//    - Return a success message with key `message` if the order is successfully placed.
//    - Return an error message with key `Error` if there is an issue processing the order.