package VendingMachine;

import VendingMachine.controller.VendingMachineController;
import VendingMachine.dao.AuditDao;
import VendingMachine.dao.AuditDaoFileImpl;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.VendingMachineDaoFileImpl;
import VendingMachine.service.VendingMachineServiceLayer;
import VendingMachine.service.VendingMachineServiceLayerImpl;
import VendingMachine.ui.UserIO;
import VendingMachine.ui.UserIOConsoleImpl;
import VendingMachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
//        UserIO myIO = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIO);
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//        AuditDao auditDao = new AuditDaoFileImpl();
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, auditDao);
//        VendingMachineController controller = new VendingMachineController(myView, myService);

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("VendingMachine");
        appContext.refresh();

        VendingMachineController controller = appContext.getBean("controller", VendingMachineController.class);

        controller.run();
    }
}
