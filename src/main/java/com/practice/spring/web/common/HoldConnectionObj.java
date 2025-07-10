package com.practice.spring.web.common;

import java.sql.Connection;

import lombok.Data;

@Data
public class HoldConnectionObj {
	
	private Connection conn;
	
}
