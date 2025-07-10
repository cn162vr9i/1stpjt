package com.practice.spring.web.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.practice.spring.web.common.ConnectionHolderInstHandler;
import com.practice.spring.web.common.HoldConnectionObj;
import com.practice.spring.web.dao.S01Dao;
import com.practice.spring.web.form.S01Form;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/S01")
@Controller
@Slf4j
public class Screen01Controller {

	public static final String INSTANCE_KEY = "INST01";

	@PostMapping(path = "", params = "action01")
	public ModelAndView init(final HttpServletRequest request, final ModelAndView mov,
			@ModelAttribute("s01Form") final S01Form form) throws Exception {
		log.debug("-----------------------------< Screen01Controller(init) >-----------------------------");

		//		log.debug(ReflectionToStringBuilder.toString(mov));
		//		
		//		//DBのコネクションを取得
		//		HoldConnectionObj instance = new HoldConnectionObj();
		//		instance.setConn(ConnectionProvider.getConnection());
		//		HttpSession session = request.getSession(false);
		//		ConnectionHolderInstHandler.put(session, INSTANCE_KEY, instance);
		HttpSession session = request.getSession(false);
		if (session != null) {
			HoldConnectionObj instance = ConnectionHolderInstHandler.get(session, INSTANCE_KEY);
			if (instance != null) {
				S01Dao.showUserMaster(instance.getConn());
			} else {
				log.debug("instanceがnullです");
			}
		}

		if (form.isRdo1()) {
			if (session != null) {
				log.debug("sessionを殺しますのでLogを参照");
//				ConnectionHolderInstHandler.remove(session);
				session.invalidate();
				mov.setViewName("s01");
				System.gc(); // GCを呼ぶ（※強制ではない）
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
					log.debug("エラーだが無視");
				}
				log.debug("------------ session除去終了 ------------");
			} else {
				mov.setViewName("forward:/");
			}
		} else {
			mov.setViewName("forward:/");
		}
		mov.addObject("teststr", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		return mov;
	}
}
