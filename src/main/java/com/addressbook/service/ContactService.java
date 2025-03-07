package com.addressbook.service;

import com.addressbook.dto.ContactDTO;
import com.addressbook.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

@Service
public class ContactService {

    // In-memory storage for contacts
    private final List<Contact> contacts = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // For generating unique IDs

    // Convert Contact to ContactDTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setName(contact.getName());
        contactDTO.setPhoneNumber(contact.getPhoneNumber());
        contactDTO.setEmail(contact.getEmail());
        return contactDTO;
    }

    // Convert ContactDTO to Contact
    private Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(idCounter.getAndIncrement()); // Generate a unique ID
        contact.setName(contactDTO.getName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmail(contactDTO.getEmail());
        return contact;
    }

    // Get all contacts
    public List<ContactDTO> getAllContacts() {
        return contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a contact by ID
    public ContactDTO getContactById(Long id) {
        return contacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Create a new contact
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        contacts.add(contact);
        return convertToDTO(contact);
    }

    // Update an existing contact
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        for (Contact contact : contacts) {
            if (contact.getId().equals(id)) {
                contact.setName(contactDTO.getName());
                contact.setPhoneNumber(contactDTO.getPhoneNumber());
                contact.setEmail(contactDTO.getEmail());
                return convertToDTO(contact);
            }
        }
        return null; // Contact not found
    }

    // Delete a contact
    public void deleteContact(Long id) {
        contacts.removeIf(contact -> contact.getId().equals(id));
    }
}