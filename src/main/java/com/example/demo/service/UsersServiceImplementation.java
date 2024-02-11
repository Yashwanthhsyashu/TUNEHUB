package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
@Service
public class UsersServiceImplementation implements UsersService{
	@Autowired
	UsersRepository repo;
	@Override
	public String addUsers(Users user) {
		// TODO Auto-generated method stub
		repo.save(user);
		return "user added successfully";
	}
	@Override
	public boolean emailExits(String email) {
		// TODO Auto-generated method stub
		if(repo.findByEmail(email)==null) {
			return false;
		}else {
		return true;
		}
	}
	@Override
	public boolean validateUser(String email, String password) {
		// TODO Auto-generated method stub
		Users user=repo.findByEmail(email);
		if(password.equals(user.getPassword())) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public String getRole(String email) {
		// TODO Auto-generated method stub
		Users user=repo.findByEmail(email);
		return user.getRole();
	}
	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		repo.save(user);
		
	}
	@Override
	public Users getUser(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

}
