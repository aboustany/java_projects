package FlooringMastery.dao;

import FlooringMastery.dto.Product;
import FlooringMastery.exceptions.FlooringPersistenceException;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts() throws FlooringPersistenceException;
    Product getProductByType(String productType) throws FlooringPersistenceException;
}
