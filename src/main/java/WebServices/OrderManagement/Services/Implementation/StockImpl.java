package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.ProductEntity;
import WebServices.OrderManagement.Entity.StockEntity;
import WebServices.OrderManagement.Repositories.StockRepo;
import WebServices.OrderManagement.Services.ProductService;
import WebServices.OrderManagement.Services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
/**
 * This class implements the StockService interface.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Stocks.
 */
@Service
public class StockImpl implements StockService {
    private final StockRepo _StockRepo;
    private final ProductService _ProductService;
    public StockImpl(StockRepo stockRepo, ProductService productService) {
        _StockRepo = stockRepo;
        _ProductService = productService;
    }

    @Override
    public StockEntity CreateStock(StockEntity stock) {
        try {
            if (checkIfEntityParametersNullOrEmpty(stock)) {
                ProductEntity product = _ProductService.GetProductById(stock.getStockProduct().getId());
                stock.setStockProduct(product);
                StockEntity savedStock = _StockRepo.save(stock);
                return savedStock;
            }
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Empty Fields in stock entity");

        }catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(),ex.getReason());
        }
    }

    @Override
    public StockEntity GetStockById(Long id) {
        StockEntity stock = _StockRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock was not found"));
        if (stock != null){
            return stock;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock was not found");
    }

    @Override
    public List<StockEntity> GetAllStocks() {
        List<StockEntity> list = _StockRepo.findAll();
        if (list!=null){
            return list;
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Stock is empty");
    }

    @Override
    public StockEntity UpdateStock(Long id, StockEntity stock) {
        StockEntity old = GetStockById(id);

        if (old != null && checkIfEntityParametersNullOrEmpty(stock)){
            old.setUpdatedAt(stock.getUpdatedAt());
            old.setQuantity(stock.getQuantity());
            ProductEntity product = _ProductService.GetProductById(stock.getStockProduct().getId());
            old.setStockProduct(product);
            stock = _StockRepo.save(old);
            return stock;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"stock was not found");
    }



    @Override
    public void DeleteStock(Long id) {
        StockEntity stock = GetStockById(id);
        if (stock != null){
            _StockRepo.delete(stock);
        }
    }
    public boolean checkIfEntityParametersNullOrEmpty(StockEntity entity) {
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

                if (value instanceof String && ((String) value).isBlank()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
