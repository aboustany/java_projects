package ContactList.classroster;

import ContactList.controller.ClassRosterController;
import ContactList.dao.ClassRosterDao;
import ContactList.dao.ClassRosterDaoFileImpl;
import ContactList.ui.ClassRosterView;
import ContactList.ui.UserIO;
import ContactList.ui.UserIOConsoleImpl;

public class App {

    public static void main(String[] args) {
        UserIO myIO = new UserIOConsoleImpl();
        ClassRosterView myView = new ClassRosterView(myIO);
        ClassRosterDao myDao = new ClassRosterDaoFileImpl();
        ClassRosterController controller = new ClassRosterController(myDao, myView);
        controller.run();
    }
}
