package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.ProductOrderEntity;
import WebServices.OrderManagement.Repositories.OrderRepo;
import WebServices.OrderManagement.Repositories.ProductOrderRepo;
import WebServices.OrderManagement.Repositories.ProductRepo;
import WebServices.OrderManagement.Services.ProductOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ProductOrderImpl implements ProductOrderService {
    private final ProductOrderRepo _ProductOrderRepo;


    public ProductOrderImpl(ProductOrderRepo productOrderRepo) {
        _ProductOrderRepo = productOrderRepo;

    }

    @Override
    public ProductOrderEntity CreateProductOrder(ProductOrderEntity productOrder) {
        if (checkIfEntityParametersNullOrEmpty(productOrder)){
            ProductOrderEntity SavedTransaction = _ProductOrderRepo.save(productOrder);
            return SavedTransaction;
        }
        return null;
    }

    @Override
    public ProductOrderEntity GetOrderById(Long id) {
        ProductOrderEntity productOrder = _ProductOrderRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product_Order was not found"));
        if (productOrder != null){
            return productOrder;
        }
        return null;
    }

    @Override
    public List<ProductOrderEntity> GetAllProductOrders() {
        List<ProductOrderEntity> list = _ProductOrderRepo.findAll();
        if (list != null){
            return list;
        }
        return null;
    }

    @Override
    public ProductOrderEntity UpdateOrder(Long id, ProductOrderEntity productOrder) {
        ProductOrderEntity old = GetOrderById(id);
        if (old != null){
            old.setVat(productOrder.getVat());
            old.setPrice(productOrder.getPrice());
            old.setQuantity(productOrder.getQuantity());
            productOrder = _ProductOrderRepo.save(old);
            return productOrder;
        }
        return null;
    }

    @Override
    public void DeleteOrder(Long id) {
        ProductOrderEntity productOrder = GetOrderById(id);
        if (productOrder != null){
            _ProductOrderRepo.delete(productOrder);
        }
    }
    public boolean checkIfEntityParametersNullOrEmpty(ProductOrderEntity entity) {
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
