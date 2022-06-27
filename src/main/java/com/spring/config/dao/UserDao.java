package com.spring.config.dao;

import java.util.List;

import com.spring.config.pojo.User;

public interface UserDao {

	int create(User user);

	List<User> read();

	List<User> findUserById(int userId);

	int update(User user);

	int delete(int userID);

}
