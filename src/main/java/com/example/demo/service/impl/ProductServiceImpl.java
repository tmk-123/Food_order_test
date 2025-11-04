package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

import com.example.demo.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
    // Service phụ thuộc vào Repository để đọc/ghi dữ liệu. Dùng final vì dependency này không thay đổi.
    private final ProductRepository repo;

    // Tại Sao Cần Constructor Injection?
    // Bạn nói đúng, Spring có thể tiêm dependency một cách tự động thông qua Field Injection (@Autowired trên trường private ProductRepository repo;). Tuy nhiên, Constructor Injection được ưu tiên vì những lý do sau:

    // 1. Đảm bảo Tính Bất biến và Hợp đồng Rõ ràng (Immutability)
    // Sử dụng final: Khi bạn khai báo trường là private final ProductRepository repo;, bạn đảm bảo rằng dependency này không bao giờ bị thay đổi sau khi đối tượng được tạo. Constructor là nơi duy nhất bạn có thể gán giá trị cho trường final.
    // Hợp đồng Rõ ràng: Constructor định nghĩa một hợp đồng bắt buộc phải có cho đối tượng ProductServiceImpl. Bất kỳ ai muốn tạo đối tượng này đều phải cung cấp ProductRepository.
    // 2. Dễ dàng Kiểm thử Đơn vị (Unit Testing)
    // Đây là lợi ích lớn nhất. Constructor Injection giúp bạn kiểm thử độc lập lớp ProductServiceImpl mà không cần môi trường Spring Boot:
    // Bạn có thể tự tạo đối tượng ProductServiceImpl trong code kiểm thử và truyền vào một đối tượng Repository giả lập (Mock), như ví dụ trước.
    // Ví dụ: ProductService service = new ProductServiceImpl(mockRepo);
    // Ngược lại, Field Injection làm cho việc này khó khăn hơn vì bạn phải sử dụng reflection hoặc các công cụ đặc biệt để tiêm đối tượng Mock vào trường private.
    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteProductById(Long id) {
        repo.deleteById(id);
    }
}
