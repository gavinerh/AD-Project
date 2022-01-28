package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository uRepo;
	
	@Override
	public List<User> findAll() {
		return uRepo.findAll();
	}

	@Override
	public User findById(Long id) {
		return uRepo.getById(id);
	}

	@Override
	public User findUserByEmail(String email) {
		return uRepo.findUserByEmail(email);
	}

	@Override
	public User save(User user) {
		return uRepo.save(user);
	}

	@Override
	public void deleteUser(User user) {
		uRepo.delete(user);
	}

}
