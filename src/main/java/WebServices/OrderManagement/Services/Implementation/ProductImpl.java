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
            return savedProduct;
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Error in creating product empty fields");
    }

    @Override
    public ProductEntity GetProductById(Long id) {
        ProductEntity product = _ProductRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product Not Found"));
        if (product != null){
            return product;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product was not found");
    }

    @Override
    public List<ProductEntity> GetAllProducts() {
        List<ProductEntity> list = _ProductRepo.findAll();
        if (list != null){
            return list;
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT,"empty product list ");
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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product was not found!");
    }

    @Override
    public void DeleteProduct(Long id) {
        ProductEntity product = GetProductById(id);
        if(product != null){
            _ProductRepo.delete(product);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product was not found");
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
                if (field.getName().equals("productOrders")){
                    continue;
                }
                else {
                    Object value = field.get(entity);

                    if (value == null) {
                        return false;
                    }

                    if (value instanceof String && ((String) value).isBlank()) {
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
