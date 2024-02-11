package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Song;
import com.example.demo.entity.Users;
import com.example.demo.service.SongService;
import com.example.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;
@Controller
public class UsersController {
	@Autowired
	UsersService service;
	
	@Autowired
	SongService songService;
	@PostMapping("/registration")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus=service.emailExits(user.getEmail());
		if(userStatus==false) {
				service.addUsers(user);
				System.out.println("user added");
		}else {
			System.out.println("user already exits");
		}
				return "login";
		
	}
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,@RequestParam("password") String password,HttpSession session,Model model) {
		if(service.validateUser(email,password)==true) {
			session.setAttribute("email", email);
			if(service.getRole(email).equals("admin")) {
				return "adminHome";
			}else {
				Users user=service.getUser(email);
				boolean userStatus=user.isPremium();
				List<Song> songsList = songService.fetchAllSongs();
				model.addAttribute("songs", songsList);
				
				model.addAttribute("isPremium", userStatus);
				
				return "customerHome";
			}
		}else {
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
