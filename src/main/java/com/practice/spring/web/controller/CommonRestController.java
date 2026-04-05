package com.practice.spring.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api")
@Slf4j
public class CommonRestController {

	public ResponseEntity<?> killSession(final HttpServletResponse response) {

//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Custom-Header", "CustomValue");
		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setMaxAge(0); // 有効期限を過去に設定
//		cookie.setPath("/"); // パスを指定（必要に応じて）
		response.addCookie(cookie);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}

}
