package com.app.controller;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.app.dto.TokenDTO;
import com.app.service.JwtService;
@Slf4j
@RestController
public class ApiController {

	@GetMapping("/")
	public String home() {
		return "JwtService Start!!";
	}
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public TokenDTO login(@RequestParam Map<String, String> params) {
		return jwtService.login(params);
	}
	
	@PostMapping("/getName")
	public String check(Authentication auth) {
		return auth.getName()+" "+auth.getAuthorities();
	}

	@GetMapping("/home")
	public String home2(Authentication auth) {
		String role = auth.getAuthorities().toString();
		log.info ("role : {}", role);
		String data = "";
		if("[ROLE_ADMIN]".equals(auth.getAuthorities().toString())){
			data = "<h1>스프링 웹 어플리케이션<h1>" +
					"<h2>"+auth.getName()+" 관리자님 환영합니다.<h2>" +
					"<h3>" + auth.getName() + "님은 " +auth.getAuthorities()+"(관리자) 권한을 가지고 계십니다.";
			return data;
		} else if("[ROLE_USER]".equals(auth.getAuthorities().toString())) {
			data = "<h1>스프링 웹 어플리케이션<h1>" +
					"<h2>"+auth.getName() +" 님 환영합니다.<h2>" +
					"<h3>" + auth.getName() + "님은 "+auth.getAuthorities()+"(사용자) 권한을 가지고 계십니다.";
			return data;
		}
		data = "<h1> 로그인 해라 </h1>";
		return data;
	}
	
	@PostMapping("/getPrincipal")
	public Object getPrincipal(Authentication auth) {
		return auth.getPrincipal();
	}

	@GetMapping("/makeuser")
	public String makeUser() {
		String pwd = passwordEncoder.encode("1234");
		Map<String, String> result = new HashMap<>();
		result.put("admin", pwd);
		result.put("user", pwd);
		return result.toString();
	}
	
}
