package com.example.demo.service;

import java.util.List;
import java.util.Optional;  // 追加

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		contactRepository.save(contact);
	}

	@Override
	public List<Contact> findAll() {
		return contactRepository.findAll();
	}

	@Override
	public Optional<Contact> findById(Long id) {  // 修正
		return contactRepository.findById(id);
	}

	@Override
	public void updateContact(Long id, ContactForm contactForm) {
		Optional<Contact> optionalContact = contactRepository.findById(id);
		if (optionalContact.isPresent()) {
			Contact contact = optionalContact.get();
			contact.setLastName(contactForm.getLastName());
			contact.setFirstName(contactForm.getFirstName());
			contact.setEmail(contactForm.getEmail());
			contact.setPhone(contactForm.getPhone());
			contact.setZipCode(contactForm.getZipCode());
			contact.setAddress(contactForm.getAddress());
			contact.setBuildingName(contactForm.getBuildingName());
			contact.setContactType(contactForm.getContactType());
			contact.setBody(contactForm.getBody());
			contactRepository.save(contact);
		}
	}

	@Override
	public void deleteContact(Long id) {
		contactRepository.deleteById(id);
	}
}
