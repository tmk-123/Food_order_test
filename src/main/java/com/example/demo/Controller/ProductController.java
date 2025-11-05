package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.ui.Model;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Category;
@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    // Gọi là Constructor Injection — cách khuyên dùng, vì:
    // Dễ test hơn
    // Đảm bảo dependency luôn có (không bị null)
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    // Trình duyệt → /products (GET)
    //    ↓
    // ProductController.getAllProducts()
    //    ↓
    // ProductServiceImpl.getAll() → Repository → DB
    //    ↓
    // Thêm vào model
    //    ↓
    // Render file templates/product.html

    // Áp dụng cho HTTP GET request — tức là khi người dùng truy cập http://localhost:8080/products.
    @GetMapping
    //  Model: Đối tượng chứa dữ liệu bạn muốn truyền từ Controller sang View (HTML).
    public String getAllProduct(Model model) {

        // Gọi đến tầng Service để lấy toàn bộ sản phẩm từ DB.
        // (Service gọi Repository → truy vấn DB).
        List<Product> products = productService.getAllProducts();

        List<Category> categories = categoryService.findAll();
        // Gắn danh sách sản phẩm vào Model với tên products. Trong file HTML, bạn có thể gọi ${products} để lấy dữ liệu này.
        model.addAttribute("products", products);

        
        model.addAttribute("categories", categories);

        // Thêm một đối tượng Product rỗng vào model để dùng làm mẫu cho form thêm sản phẩm mới
        model.addAttribute("product", new Product());

        // Spring Boot sẽ tìm file src/main/resources/templates/product.html để render trang.
        return "product"; 
    }

    
    // Người dùng nhập form (product.html)
    // → Submit POST /products
    // → ProductController.addProduct()
    //     → ProductService.save()
    //     → ProductRepository.save()
    //         → DB (INSERT)
    // ← Redirect về /products
    // → Hiển thị lại danh sách

    // Xử lý HTTP POST request, khi người dùng submit form thêm sản phẩm., vì chỉ có 1 post nên không cần "/.."
    @PostMapping
    // Spring Boot tự động lấy dữ liệu từ form HTML (tên input trùng với thuộc tính Product) và gán vào đối tượng product.
    public String saveProduct(@ModelAttribute Product product) {
        // Gọi tầng Service để lưu sản phẩm vào DB.
        productService.saveProduct(product);
        
        // Sau khi lưu xong, chuyển hướng (redirect) trình duyệt về lại trang /products để hiển thị danh sách sản phẩm đã cập nhật.
        return "redirect:/products";
    }

    // /edit/{id} = đường dẫn có phần động tên là {id}
    // → Spring hiểu rằng {id} sẽ được thay bằng một số cụ thể.
    @GetMapping("/edit/{id}")
    // PathVariable: Lấy giá trị từ phần động của URL và gán vào biến id.
    public String editProduct(@PathVariable Long id, Model model) {
        // Gọi tầng Service để lấy sản phẩm theo ID từ DB.
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.findAll();

        // Thêm sản phẩm vào model để hiển thị trong form chỉnh sửa.
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
