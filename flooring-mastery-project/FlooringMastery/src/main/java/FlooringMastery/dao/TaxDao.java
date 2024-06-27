package FlooringMastery.dao;

import FlooringMastery.dto.Tax;
import FlooringMastery.exceptions.FlooringPersistenceException;
import java.util.List;

public interface TaxDao {
    List<Tax> getTaxes() throws FlooringPersistenceException;
    Tax getTaxByState(String stateAbbreviation) throws FlooringPersistenceException;

}
