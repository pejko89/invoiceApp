package com.alpey.invoice.feature.user;

import java.util.List;

public interface IUserService {

	UserDto createUser(UserDto dto);

	UserDto update(UserDto dto, String username);

	String delete(String username);

	UserDto findByUsername(String username);

	UserDto findByEmail(String email);

	List<UserDto> findAllUsers();

}
