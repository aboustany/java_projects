package FlooringMastery.dao;

import FlooringMastery.dto.Product;
import FlooringMastery.exceptions.FlooringPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ProductDaoDbImpl implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoDbImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> getProducts() throws FlooringPersistenceException {
        final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
        try {
            return jdbcTemplate.query(SELECT_ALL_PRODUCTS, new ProductMapper());
        }
        catch (Exception e) {
            throw new FlooringPersistenceException("Could not retrieve products", e);
        }
    }

    @Override
    public Product getProductByType(String productType) throws FlooringPersistenceException {
        final String SELECT_PRODUCT_BY_TYPE = "SELECT * FROM products WHERE productType = ?";
        try {
            return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_TYPE, new ProductMapper(), productType);
        } catch (Exception e) {
            throw new FlooringPersistenceException("Could not find product with type: " + productType, e);
        }
    }

    private static final class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {
            String productType = rs.getString("productType");
            BigDecimal costPerSquareFoot = rs.getBigDecimal("costPerSquareFoot");
            BigDecimal LaborCostPerSquareFoot = rs.getBigDecimal("laborCostPerSquareFoot");
            return new Product(productType, costPerSquareFoot, LaborCostPerSquareFoot);
        }
    }
}
