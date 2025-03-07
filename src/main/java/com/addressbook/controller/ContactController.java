package com.addressbook.controller;

import com.addressbook.model.Contact;
import com.addressbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // GET: Fetch all contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    // GET: Fetch a contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST: Create a new contact
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    // PUT: Update an existing contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        Contact updatedContact = contactService.updateContact(id, contactDetails);
        if (updatedContact != null) {
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Delete a contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}