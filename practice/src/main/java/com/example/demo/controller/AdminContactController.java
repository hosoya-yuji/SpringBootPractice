package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/contacts")
public class AdminContactController {

	@Autowired
	private ContactService contactService;

	@GetMapping
	public String listContacts(Model model) {
	List<Contact> contacts = contactService.findAll();
		model.addAttribute("contacts", contacts);
		return "adminContactList";
	}

	@GetMapping("/{id}")
	public String viewContact(@PathVariable Long id, Model model) {
		Optional<Contact> optionalContact = contactService.findById(id);
		if (optionalContact.isPresent()) {
			model.addAttribute("contact", optionalContact.get());
			return "adminContactDetail";
		} else {
			return "redirect:/admin/contacts"; // 適切なリダイレクト先を設定してください
		}
	}

	@GetMapping("/{id}/edit")
	public String editContact(@PathVariable Long id, Model model) {
		Optional<Contact> optionalContact = contactService.findById(id);
		if (optionalContact.isEmpty()) {
			return "redirect:/admin/contacts"; // 適切なリダイレクト先を設定してください
		}

		Contact contact = optionalContact.get();
		ContactForm contactForm = new ContactForm();
		contactForm.setId(contact.getId());
		contactForm.setLastName(contact.getLastName());
		contactForm.setFirstName(contact.getFirstName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhone(contact.getPhone());
		contactForm.setZipCode(contact.getZipCode());
		contactForm.setAddress(contact.getAddress());
		contactForm.setBuildingName(contact.getBuildingName());
		contactForm.setContactType(contact.getContactType());
		contactForm.setBody(contact.getBody());
		contactForm.setCreatedAt(contact.getCreatedAt());
		contactForm.setUpdatedAt(contact.getUpdatedAt());

		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId", id);
		return "adminContactEdit";
	}

	@PostMapping("/{id}/edit")
	public String updateContact(@PathVariable Long id, @Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult result) {
		if (result.hasErrors()) {
			return "adminContactEdit";
		}
		contactService.updateContact(id, contactForm);
		return "redirect:/admin/contacts";
	}

	@PostMapping("/{id}/delete")
	public String deleteContact(@PathVariable Long id) {
		contactService.deleteContact(id);
		return "redirect:/admin/contacts";
	}
}
