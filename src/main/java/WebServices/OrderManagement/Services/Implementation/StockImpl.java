package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.StockEntity;
import WebServices.OrderManagement.Repositories.StockRepo;
import WebServices.OrderManagement.Services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class StockImpl implements StockService {
    private final StockRepo _StockRepo;

    public StockImpl(StockRepo stockRepo) {
        _StockRepo = stockRepo;
    }

    @Override
    public StockEntity CreateStock(StockEntity stock) {
        if (checkIfEntityParametersNullOrEmpty(stock)){
            StockEntity savedStock = _StockRepo.save(stock);
            return savedStock;
        }
        return null;
    }

    @Override
    public StockEntity GetStockById(Long id) {
        StockEntity stock = _StockRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Stock was not found"));
        if (stock != null){
            return stock;
        }
        return null;
    }

    @Override
    public List<StockEntity> GetAllStocks() {
        List<StockEntity> list = _StockRepo.findAll();
        if (list!=null){
            return list;
        }
        return null;
    }

    @Override
    public StockEntity UpdateStock(Long id, StockEntity stock) {
        StockEntity old = GetStockById(id);
        if (old != null){
            old.setUpdatedAt(stock.getUpdatedAt());
            old.setQuantity(stock.getQuantity());
            if(stock.getStockProduct() != null){
                old.setStockProduct(stock.getStockProduct());
            }

            stock = _StockRepo.save(old);
            return stock;
        }
        return null;
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
