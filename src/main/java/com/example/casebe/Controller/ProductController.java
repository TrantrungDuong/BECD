package com.example.casebe.Controller;
import com.example.casebe.Model.Category;
import com.example.casebe.Model.Product;
import com.example.casebe.Repository.CategoryRepository;
import com.example.casebe.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Product product) {
        // Validate product fields
        if (product.getName() == null || product.getBrand() == null || product.getPrice() == null ||
                product.getDescription() == null || product.getImageUrl() == null || product.getCategory() == null || product.getCategory().getId() == null) {
            return new ResponseEntity<>("Dữ liệu không đầy đủ", HttpStatus.BAD_REQUEST);
        }
        Long categoryId = product.getCategory().getId();  // Get the categoryId from the product object
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));  // Fetch the Category entity

        product.setCategory(category);  // Assign the full Category entity to the product

        // Set the current date
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentFormatDateTime = current.format(formatter);
        product.setCreateAt(currentFormatDateTime);

        // Save the product with the associated category
        productRepository.save(product);

        return new ResponseEntity<>("Thêm sản phẩm thành công", HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Product productDetails) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()) {
            return new ResponseEntity<>("Sản phẩm không tồn tại", HttpStatus.NOT_FOUND);
        }

        Product existingProduct = existingProductOptional.get();

        existingProduct.setName(productDetails.getName());
        existingProduct.setBrand(productDetails.getBrand());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setImageUrl(productDetails.getImageUrl());

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentFormatDateTime = current.format(formatter);
        existingProduct.setUpdateAt(currentFormatDateTime);

        productRepository.save(existingProduct);

        return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         productRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Product>> sortByPrice(){
        List<Product> products =  productRepository.findAllByOrderByPriceAsc();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findByID(@PathVariable long id){
        Optional<Product> product = productRepository.findById(id);
        return new  ResponseEntity<> (product, HttpStatus.OK);
    }
}
