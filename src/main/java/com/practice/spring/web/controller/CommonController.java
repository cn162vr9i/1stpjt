package com.practice.spring.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.practice.spring.web.common.ConnectionHolderInstHandler;
import com.practice.spring.web.common.HoldConnectionObj;
import com.practice.spring.web.form.S01Form;
import com.practice.spring.web.util.ConnectionProvider;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommonController {

	@PostMapping("")
	public ModelAndView initPost(final HttpServletRequest request, final ModelAndView mov) throws Exception {
		return init(request, mov);
	}

	@GetMapping("")
	public ModelAndView init(final HttpServletRequest request, final ModelAndView mov) throws Exception {
		log.debug("-----------------------------< init >-----------------------------");
		log.debug(ReflectionToStringBuilder.toString(mov));

		//DBのコネクションを取得
		HoldConnectionObj instance = new HoldConnectionObj();
		instance.setConn(ConnectionProvider.getConnection());
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
			log.debug("session=[{}]",session.getId());
			ConnectionHolderInstHandler.put(session, Screen01Controller.INSTANCE_KEY, instance);
		}

		mov.addObject("s01Form", new S01Form());
		mov.addObject("teststr", "最初の画面");
		mov.setViewName("s01");
		return mov;
	}
}
