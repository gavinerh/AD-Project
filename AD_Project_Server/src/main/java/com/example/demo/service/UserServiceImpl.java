package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.UserCredential;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository uRepo;
	
	@Override
	public List<UserCredential> findAll() {
		return uRepo.findAll();
	}

	@Override
	public UserCredential findById(Long id) {
		return uRepo.getById(id);
	}

	@Override
	public UserCredential findUserByEmail(String email) {
		return uRepo.findUserByEmail(email);
	}

	@Override
	public UserCredential save(UserCredential user) {
		return uRepo.save(user);
	}

	@Override
	public void deleteUser(UserCredential user) {
		uRepo.delete(user);
	}

}
