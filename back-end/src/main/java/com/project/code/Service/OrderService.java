package com.project.code.Service;

import com.project.code.Controller.GlobalExceptionHandler;
import com.project.code.Model.*;
import com.project.code.Repo.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderService {
    private CustomerRepository customerRepository;
    private StoreRepository storeRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private OrderItemRepository orderItemRepository;
    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;

// 1. **saveOrder Method**:
//    - Processes a customer's order, including saving the order details and associated items.
//    - Parameters: `PlaceOrderRequestDTO placeOrderRequest` (Request data for placing an order)
//    - Return Type: `void` (This method doesn't return anything, it just processes the order)
public void saveOrder(PlaceOrderRequestDTO placeOrderRequest){
    placeOrderRequest.setCustomerName(placeOrderRequest.getCustomerName());
    placeOrderRequest.setCustomerEmail(placeOrderRequest.getCustomerEmail());
    placeOrderRequest.setCustomerPhone(placeOrderRequest.getCustomerPhone());
    placeOrderRequest.setDatetime(placeOrderRequest.getDatetime());
    placeOrderRequest.setStoreId(placeOrderRequest.getStoreId());
    placeOrderRequest.setPurchaseProduct(placeOrderRequest.getPurchaseProduct());
    placeOrderRequest.setTotalPrice(placeOrderRequest.getTotalPrice());
}
// 2. **Retrieve or Create the Customer**:
//    - Check if the customer exists by their email using `findByEmail`.
//    - If the customer exists, use the existing customer; otherwise, create and save a new customer using `customerRepository.save()`.
public Customer retrieveOrCreateCustomer(String email, String name, String phone) {
    // 1. Check if the customer exists by their email
    Optional<Customer> existingCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
    if (existingCustomer.isPresent()) {
        // If the customer exists, return the existing customer
        return existingCustomer.get();
    } else {
        // If the customer doesn't exist, create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setEmail(email);
        newCustomer.setName(name);
        newCustomer.setPhone(phone);
        // Save the new customer to the database
        return customerRepository.save(newCustomer);
    }
}

// 3. **Retrieve the Store**:
//    - Fetch the store by ID from `storeRepository`.
//    - If the store doesn't exist, throw an exception. Use `storeRepository.findById()`.

public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId) // Returns an Optional<Store>
              .orElseThrow(() -> new GlobalExceptionHandler.StoreNotFoundException(storeId)); // Throws if empty
    }


// 4. **Create OrderDetails**:
//    - Create a new `OrderDetails` object and set customer, store, total price, and the current timestamp.
// - Set the order date using `java.time.LocalDateTime.now()` and save the order with `orderDetailsRepository.save()`.

    public void createOrderDetails(Long customer, Long store_id,  Double totalPrice, LocalDateTime date)  {
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setCustomer(customerRepository.getById(customer));
    orderDetails.setStore(storeRepository.getById(store_id));
    orderDetails.setTotalPrice(totalPrice);
    orderDetails.setDate(date);
    orderDetailsRepository.save(orderDetails);
    }

// 5. **Create and Save OrderItems**:
//    - For each product purchased, find the corresponding inventory, update stock levels, and save the changes using `inventoryRepository.save()`.
//    - Create and save `OrderItem` for each product and associate it with the `OrderDetails` using `orderItemRepository.save()`.

   public void createOrderItems(Long order_id, Long product_id, Integer quantity, Double price)  {
    OrderItem orderItem = new OrderItem();
    Inventory inventoryItem = new Inventory();
    Product product = new Product();
    orderItem.setOrder(orderDetailsRepository.getById(order_id));
    orderItem.setProduct(orderItemRepository.findById(product_id));
    orderItem.setQuantity(quantity);
    orderItem.setPrice(price);
    orderItemRepository.save(orderItem);
    inventoryItem.setStockLevel(inventoryItem.getStockLevel() - quantity);
    inventoryRepository.save(inventoryItem);
   }
}
