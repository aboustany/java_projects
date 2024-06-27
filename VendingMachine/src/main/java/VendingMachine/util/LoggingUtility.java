package VendingMachine.util;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.AuditDaoFileImpl;
import VendingMachine.exceptions.VendingMachinePersistenceException;

public class LoggingUtility {

    public static final AuditDao auditDao = new AuditDaoFileImpl();

    public static void log(String message, Logger logger) throws VendingMachinePersistenceException {
        logger.log(message);
    }
}
