package com.practice.spring.web.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class S01Dao {

	public static void showUserMaster(Connection conn) {

		QueryRunner runner = new QueryRunner();
		ResultSetHandler<List<Map<String, Object>>> rsh = new MapListHandler();
		try {
			List<Map<String, Object>> list = runner.query(conn, "select * from M_USER", rsh);
			int cnt = 0;
			for (Map<String, Object> map : list) {
				cnt++;
				// 1行目のみカラム名も出力する
				if (cnt == 1) {
					for (String key : map.keySet()) {
						System.out.print(key + " | ");
					}
					System.out.println();
				}
				// 値を出力する
				for (String key : map.keySet()) {
					System.out.print(String.valueOf(map.get(key)) + " | ");
				}
				System.out.println();
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
