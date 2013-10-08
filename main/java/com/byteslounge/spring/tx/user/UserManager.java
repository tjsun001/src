package com.byteslounge.spring.tx.user;

import com.byteslounge.spring.tx.model.User;

import java.util.List;

public interface UserManager {

	void insertUser(User user) throws Exception;

    void deleteUser(String socialSecurityNumber) throws Exception ;
    void deleteUser(User user) throws Exception ;

	User getUserById(int userId);
	
	User getUser(String socialSecurityNumber);
	
	List<User> getUsers();
}
