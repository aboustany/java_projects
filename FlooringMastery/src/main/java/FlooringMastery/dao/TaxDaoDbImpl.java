package FlooringMastery.dao;

import FlooringMastery.dto.Tax;
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
public class TaxDaoDbImpl implements TaxDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TaxDaoDbImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Tax> getTaxes() throws FlooringPersistenceException {
        final String SELECT_ALL_TAXES = "SELECT * FROM taxes";
        try {
            return jdbcTemplate.query(SELECT_ALL_TAXES, new TaxMapper());
        }
        catch (Exception e) {
            throw new FlooringPersistenceException("Could not retrieve taxes", e);
        }
    }

    @Override
    public Tax getTaxByState(String stateAbbreviation) throws FlooringPersistenceException {
        final String SELECT_TAX_BY_STATE = "SELECT * FROM taxes WHERE stateAbbreviation = ?";
        try {
            return jdbcTemplate.queryForObject(SELECT_TAX_BY_STATE, new TaxMapper(), stateAbbreviation);
        } catch (Exception e) {
            throw new FlooringPersistenceException("Could not find tax information for state: " + stateAbbreviation, e);
        }
    }

    private static final class TaxMapper implements RowMapper<Tax> {
        @Override
        public Tax mapRow(ResultSet rs, int rowNum) throws SQLException {
            String stateAbbreviation = rs.getString("stateAbbreviation");
            String stateName = rs.getString("stateName");
            BigDecimal taxRate = rs.getBigDecimal("taxRate");
            return new Tax(stateAbbreviation, stateName, taxRate);
        }
    }
}
