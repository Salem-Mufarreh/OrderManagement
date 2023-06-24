package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Repositories.ProductRepo;
import WebServices.OrderManagement.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ProductImpl implements ProductService {
    private final ProductRepo _ProductRepo;

    public ProductImpl(ProductRepo productRepo) {
        _ProductRepo = productRepo;
    }

    @Override
    public ProductEntity CreateProduct(ProductEntity product) {
        if (checkIfEntityParametersNullOrEmpty(product)){
            ProductEntity savedProduct = _ProductRepo.save(product);
            return product;
        }
        return null;
    }

    @Override
    public ProductEntity GetProductById(Long id) {
        ProductEntity product = _ProductRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product Not Found"));
        if (product != null){
            return product;
        }
        return null;
    }

    @Override
    public List<ProductEntity> GetAllProducts() {
        List<ProductEntity> list = _ProductRepo.findAll();
        if (list != null){
            return list;
        }
        return null;
    }

    @Override
    public ProductEntity UpdateProduct(Long id, ProductEntity product) {
        ProductEntity old = GetProductById(id);
        if (old != null){
            old.setName(product.getName());
            old.setPrice(product.getPrice());
            old.setVat(product.getVat());
            old.setReference(product.getReference());
            old.setStockAble(product.getStockAble());
            product = _ProductRepo.save(old);
            return product;
        }
        return null;
    }

    @Override
    public void DeleteProduct(Long id) {
        ProductEntity product = GetProductById(id);
        if(product != null){
            _ProductRepo.delete(product);
        }

    }
    public boolean checkIfEntityParametersNullOrEmpty(ProductEntity entity) {
        if (entity == null) {
            return false;
        }

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);

                if (value == null) {
                    return false;
                }

                if (value instanceof String && ((String) value).isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
