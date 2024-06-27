package org.AddressBook.addressroster;

import org.AddressBook.controller.AddressBookController;
import org.AddressBook.dao.AddressBookDaoFileImpl;
import org.AddressBook.ui.AddressBookView;
import org.AddressBook.ui.UserIO;
import org.AddressBook.ui.UserIOConsoleImpl;
import org.AddressBook.dao.AddressBookDao;

public class App {

    public static void main(String[] args) {

        UserIO io = new UserIOConsoleImpl();
        AddressBookView view = new AddressBookView(io);
        AddressBookDao dao = new AddressBookDaoFileImpl();

        AddressBookController controller = new AddressBookController(dao, view);
        controller.run();

    }
}
