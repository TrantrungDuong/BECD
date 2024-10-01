package com.example.casebe.Controller;

import com.example.casebe.Model.Cart;
import com.example.casebe.Model.CartItem;
import com.example.casebe.Model.Product;
import com.example.casebe.Repository.CartItemRepository;
import com.example.casebe.Repository.CartRepository;
import com.example.casebe.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = productRepository.findById(cartItem.getProduct().getId()).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CartItem newCartItem = new CartItem(cart, product, cartItem.getQuantity());
        cartItemRepository.save(newCartItem);
        return new ResponseEntity<>(newCartItem, HttpStatus.OK);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CartItem> cartItems = cartItemRepository.findByCart_Id(cartId);
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }
}
