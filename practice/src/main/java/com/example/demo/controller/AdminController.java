package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/admin/signup")
	public String signupForm(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "adminSignup";
	}

	@PostMapping("/admin/signup")
	public String signup(@Valid @ModelAttribute AdminForm adminForm, BindingResult result) {
		if (result.hasErrors()) {
			return "adminSignup";
		}

		Admin admin = new Admin();
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));
        
		adminService.saveAdmin(admin);
		return "redirect:/admin/signin";
	}

	@GetMapping("/admin/signin")
	public String signinForm() {
		return "adminSignin";
	}
}
