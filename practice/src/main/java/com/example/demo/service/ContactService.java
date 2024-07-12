package com.example.demo.service;

import java.util.List;
import java.util.Optional; // 追加

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

public interface ContactService {
	void saveContact(ContactForm contactForm);
	List<Contact> findAll();
	Optional<Contact> findById(Long id);  // 修正
	void updateContact(Long id, ContactForm contactForm);
	void deleteContact(Long id);
}
