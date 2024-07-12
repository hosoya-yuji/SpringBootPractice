package com.example.demo.service;

import com.example.demo.entity.Admin;

public interface AdminService {
	void saveAdmin(Admin admin);
	Admin findByEmail(String email);
}
