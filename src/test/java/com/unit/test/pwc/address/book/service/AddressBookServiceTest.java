package com.unit.test.pwc.address.book.service;


import com.pwc.address.book.entities.Contact;
import com.pwc.address.book.service.AddressBookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class AddressBookServiceTest {

    private static final String ADDRESS_BOOK_PLANET_EARTH = "AddressBookEarth";
    private static final String ADDRESS_BOOK_PLANET_MARS = "AddressBookMars";

    private AddressBookService addressBookService;

    @Before
    public void init() {
        addressBookService = new AddressBookService();
    }

    @Test
    public void testAddContact() {
        Contact addedContact = addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());
        Contact contact = getNewTestContact();
        Assert.assertTrue(contact.equals(addedContact));
    }

    @Test
    public void testRemoveContact() {
        Contact addedContact = addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());
        addressBookService.removeContact(ADDRESS_BOOK_PLANET_EARTH, addedContact.getId());
        try {
            addressBookService.removeContact(ADDRESS_BOOK_PLANET_EARTH, addedContact.getId());
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().equals("The given contact does not exist!"));
        }
    }

    @Test
    public void testGetContactsFromGivenAddressBook() {
        Contact addedContact1 = addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());
        Contact addedContact2 = addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());
        Contact contact3 = getNewTestContact();
        contact3.setFirstName("Marc");
        Contact addedContact3 = addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, contact3);

        List<Contact> retrievedContacts = addressBookService.retrieveContacts(ADDRESS_BOOK_PLANET_EARTH);
        List<Contact> addedContacts = Arrays.asList(addedContact1, addedContact2, addedContact3);

        Assert.assertTrue(addedContacts.containsAll(retrievedContacts));
        Assert.assertTrue(retrievedContacts.containsAll(addedContacts));
    }

    //
    @Test
    public void validateAddContactThrowsExceptionWhenAddressBookDoesNotExist() {
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());
        try {
            addressBookService.retrieveContacts(ADDRESS_BOOK_PLANET_MARS);
            Assert.fail("Expected an exception when the given address book does not exist.");
        } catch (Exception e) {
            Assert.assertEquals("AddressBook with the given Id does not exist", e.getMessage());
        }
    }


    @Test
    public void testAllUniqueContacts() {

        List<Contact> retrievedContactsBeforeAddingDuplicates = addressBookService.retrieveConditionalContacts("UNIQUE");

        //add contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //same as contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //contact2
        Contact contact3 = getNewTestContact();
        contact3.setFirstName("Marc");

        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, contact3);

        //same as Contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, getNewTestContact());

        //At this point we added 4 contacts but the below returned list should contain addition of 2 new records
        List<Contact> retrievedContactsAfterAddingDuplicates = addressBookService.retrieveConditionalContacts("UNIQUE");

        Assert.assertTrue(2 == retrievedContactsAfterAddingDuplicates.size() - retrievedContactsBeforeAddingDuplicates.size());
    }

    @Test
    public void testAllContacts() {

        List<Contact> retrievedContactsBeforeAddingDuplicates = addressBookService.retrieveConditionalContacts("ALL");

        //add contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //same as contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //contact2
        Contact contact3 = getNewTestContact();
        contact3.setFirstName("Marc");

        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, contact3);

        //same as Contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, getNewTestContact());

        //At this point we added 4 contacts but the below returned list should contain addition of 2 new records
        List<Contact> retrievedContactsAfterAddingDuplicates = addressBookService.retrieveConditionalContacts("ALL");

        Assert.assertTrue(4 == retrievedContactsAfterAddingDuplicates.size() - retrievedContactsBeforeAddingDuplicates.size());
    }

    @Test
    public void testCOMMONContacts() {

        List<Contact> retrievedContactsBeforeAddingDuplicates = addressBookService.retrieveConditionalContacts("COMMON");

        //add contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //same as contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_EARTH, getNewTestContact());

        //contact2
        Contact contact3 = getNewTestContact();
        contact3.setFirstName("Marc");

        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, contact3);

        //same as Contact1
        addressBookService.addContact(ADDRESS_BOOK_PLANET_MARS, getNewTestContact());

        //At this point we added 4 contacts but the below returned list should contain addition of 2 new records
        List<Contact> retrievedContactsAfterAddingDuplicates = addressBookService.retrieveConditionalContacts("ALL");

        Assert.assertTrue(8 == retrievedContactsAfterAddingDuplicates.size() - retrievedContactsBeforeAddingDuplicates.size());
    }


    private Contact getNewTestContact() {
        return new Contact("Rashmi", "Bhat", Arrays.asList("0421435534", "0432543123"));
    }

}
