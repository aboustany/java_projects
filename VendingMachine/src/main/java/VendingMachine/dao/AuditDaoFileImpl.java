package VendingMachine.dao;

import VendingMachine.exceptions.VendingMachinePersistenceException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class AuditDaoFileImpl implements AuditDao {
    public static final String AUDIT_FILE = "audit_log.txt";
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        try{
            PrintWriter out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
            LocalDateTime ld = LocalDateTime.now();
            out.println(ld.toString() + " : " + entry);
            out.flush();
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information", e);
        }
    }


}
