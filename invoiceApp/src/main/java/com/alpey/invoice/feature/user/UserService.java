package com.alpey.invoice.feature.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alpey.invoice.utils.convert.Convert;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repo;
	@Autowired
	@Lazy
	private Convert convert;
	@Autowired
	@Lazy
	private BCryptPasswordEncoder bCrypt;

	@Override
	public UserDto create(UserDto dto) {
		try {
			User user = convert.toEntity(dto);
			user.setPassword(bCrypt.encode(user.getPassword()));
			user = repo.save(user);
			System.out.println("User saved!");
			return convert.toDto(user);
		} catch (EntityExistsException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto update(UserDto dto, String username) {
		try {
			User storedUser = repo.findByUsername(username);
			User user = convert.toEntity(dto);

			BeanUtils.copyProperties(user, storedUser);

			if (dto.getPassword() != null) {
				storedUser.setPassword(bCrypt.encode(dto.getPassword()));
			}

			user = repo.save(storedUser);
			System.out.println("User updated!");
			return convert.toDto(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("");
			return new UserDto();
		}
	}

	@Override
	public String delete(String username) {
		try {
			User user = repo.findByUsername(username);
			repo.delete(user);
			System.out.println("User deleted!");
			return "User deleted!";
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Illegal input!");
			return "Illegal input!";
		}
	}

	@Override
	public UserDto findByUsername(String username) {
		try {
			User user = repo.findByUsername(username);
			return convert.toDto(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto findByEmail(String email) {
		try {
			User user = repo.findByEmail(email);
			return convert.toDto(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public List<UserDto> findAll() {
		List<UserDto> dtos = new ArrayList<>();
		List<User> users = (List<User>) repo.findAll();
		for (User user : users) {
			dtos.add(convert.toDto(user));
		}
		return dtos;
	}

}
