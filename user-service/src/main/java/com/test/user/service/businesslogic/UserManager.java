package com.test.user.service.businesslogic;

import java.util.List;

import com.test.user.service.models.User;

public interface UserManager {

	User save(User user);
	
	List<User> getAllUsers();
	
	User getUserById(Long id);
	
	User getUserByEmail(String email);
	
	User update(User user, Long id);
	
	void delete(Long id);
}