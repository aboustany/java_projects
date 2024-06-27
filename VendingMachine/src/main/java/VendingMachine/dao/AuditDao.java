package VendingMachine.dao;

import VendingMachine.exceptions.VendingMachinePersistenceException;

public interface AuditDao {
    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;


}
