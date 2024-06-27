package VendingMachine.util;

import VendingMachine.exceptions.VendingMachinePersistenceException;

@FunctionalInterface
public interface Logger {
    void log(String message) throws VendingMachinePersistenceException;
}
