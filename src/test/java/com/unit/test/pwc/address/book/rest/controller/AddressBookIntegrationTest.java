package com.unit.test.pwc.address.book.rest.controller;


import com.pwc.address.book.AddressBookApplication;
import com.pwc.address.book.entities.Contact;
import com.pwc.address.book.service.AddressBookService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class AddressBookIntegrationTest {

    private static final String TEST_ADDRESS_BOOK = "address-bookA";
    private static final String CONTACTS_END_POINT = "/api/v1/address-book/address-bookA/contacts";


    @LocalServerPort
    private int port;

    @Autowired
    private AddressBookService addressBookService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testAddContact() {
        Contact contact = new Contact("Rashmi", "Bhat", Arrays.asList("0412 123 345", "0412 123 346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<Contact> response = restTemplate.exchange(
                createURLWithPort(CONTACTS_END_POINT),
                HttpMethod.POST, entity, Contact.class);

        Assert.assertEquals(200, response.getStatusCode().value());

        addressBookService.removeContact(TEST_ADDRESS_BOOK, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testRemoveContact() {
        Contact contact = new Contact("first-name", "last-name", Arrays.asList("0412 123 345", "0412 123 346"));
        Contact addedContact = addressBookService.addContact(TEST_ADDRESS_BOOK, contact);

        String deletionUrl = createURLWithPort("/api/v1/address-book/" + TEST_ADDRESS_BOOK + "/contacts/" + addedContact.getId());

        ResponseEntity<String> response = restTemplate.exchange(
                deletionUrl,
                HttpMethod.DELETE, new HttpEntity<String>(null, null), String.class);

        Assert.assertEquals(200, response.getStatusCode().value());

        Assert.assertFalse(addressBookService.retrieveContacts(TEST_ADDRESS_BOOK).stream()
                .anyMatch(contact1 -> contact1.getId().equals(addedContact.getId())));
    }

    @Test
    public void testRetrieveAllContactsFromAddressBook() {
        Contact contact = new Contact("first-name", "last-name", Arrays.asList("0412 123 345", "0412 123 346"));
        addressBookService.addContact(TEST_ADDRESS_BOOK, contact);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/address-book/address-bookA/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void testRetrieveAllUniqueContacts() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void testRetrieveCommonContacts() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/contacts?condition=COMMON"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
     }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
