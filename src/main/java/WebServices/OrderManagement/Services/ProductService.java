package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity CreateProduct(ProductEntity product);
    ProductEntity GetProductById(Long id);
    List<ProductEntity> GetAllProducts();
    ProductEntity UpdateProduct(Long id, ProductEntity product);
    void DeleteProduct (Long id);
}
