package com.shisihai.login;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/app/login")
	public String welcome(Map<String, Object> model) {
		return "index";
	}
}
