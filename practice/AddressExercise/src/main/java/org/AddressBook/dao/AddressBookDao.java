package org.AddressBook.dao;

import org.AddressBook.dto.Address;
import org.AddressBook.exceptions.AddressBookDaoException;

import java.util.List;

public interface AddressBookDao {

    Address addAddress(String lastName, Address address)
        throws AddressBookDaoException;

    Address findAddress(String lastName)
            throws AddressBookDaoException;

    List<Address> getAllAddresses()
            throws AddressBookDaoException;

    int getAddressCount() throws AddressBookDaoException;

    Address deleteAddress(String lastName)
            throws AddressBookDaoException;


}
