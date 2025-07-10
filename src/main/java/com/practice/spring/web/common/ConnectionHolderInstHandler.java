package com.practice.spring.web.common;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHolderInstHandler {

	private static final Cache<String, Cache<String, Object>> INSTANCE_CACHE = CacheBuilder.newBuilder()
			.weakKeys()
			.removalListener(notification -> {
				System.out.println("Removed: " + notification.getKey());
				remove((String) notification.getKey());
			}).build();

	public static Cache<String, Object> get(final HttpSession session) throws Exception {
		final String sessionId = session.getId();
		return INSTANCE_CACHE.getIfPresent(sessionId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends HoldConnectionObj> T get(final HttpSession session, final String pgmName)
			throws Exception {
		Cache<String, Object> element = get(session);
		if (element == null) {
			log.warn("elementがnullです");
		}
		return (element != null) ? (T) element.getIfPresent(pgmName) : null;
	}

	public static <T extends HoldConnectionObj> void put(final HttpSession session, final String pgmName,
			final T instance) throws Exception {
		Cache<String, Object> element = get(session);
		if (element == null) {
			element = CacheBuilder.newBuilder().weakKeys().build();
		}
		if (instance == null) {
			log.warn("登録するinstanceがnullです");
		} else {
			log.debug("conn = {}", instance.getConn().hashCode());
		}
		element.put(pgmName, instance);
		String weakKey = session.getId();
		INSTANCE_CACHE.put(weakKey, element);
		log.debug("登録しました.sessionid[{}]", weakKey);
	}

	public static void remove(final HttpSession session) throws RuntimeException {
		remove(session.getId());
	}

	private static void remove(final String sessionId) throws RuntimeException {
		if (StringUtils.isBlank(sessionId)) {
			log.debug("##### session is null #####");
			return;
		}
		Cache<String, Object> cache = INSTANCE_CACHE.getIfPresent(sessionId);
		if (cache == null) {
			log.warn("cacheがnullのため解放せずに返します");
			return;
		} else {
			log.debug("{}個のcacheを解放します", cache.asMap().keySet().size());
		}

		for (String key : cache.asMap().keySet()) {
			remove(cache, key);
		}
	}

	public static void remove(final Cache<String, Object> cache, final String pgmName) throws RuntimeException {
		if (StringUtils.isBlank(pgmName)) {
			throw new IllegalArgumentException("pgmNameが未指定です");
		}

		//Connectionを取出しクローズする
		HoldConnectionObj obj = (HoldConnectionObj) cache.getIfPresent(pgmName);
		Connection conn = obj.getConn();

		try {
			if (!conn.isClosed()) {
				try {
					log.debug("rollbackします");
					conn.rollback();
				} catch (SQLException e) {
					log.error("rollbackに失敗しましました", e);
					throw new RuntimeException("rollbackに失敗しましました", e);
				} finally {
					try {
						log.debug("connをクローズします");
						conn.close();
					} catch (Exception e) {
						log.error("コネクションのクローズに失敗しました", e);
						throw new RuntimeException("コネクションのクローズに失敗しました", e);
					}
				}
			}
		} catch (SQLException e) {
			log.error("コネクションのクローズに失敗しました", e);
			throw new RuntimeException("コネクションのクローズに失敗しました", e);
		}
	}

	public static void cleanup() {
		INSTANCE_CACHE.cleanUp();
	}
	public static void invalidate(String sessionId) {
		INSTANCE_CACHE.invalidate(sessionId);
	}
}
