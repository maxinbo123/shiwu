package com.example.trading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NZP
 */
public class BaseController {
	public static Logger logger = LoggerFactory.getLogger("ctbizweb");
	public static final String hostname = System.getProperty("hostname");



	/**
	 * 处理页面传入参数
	 * 
	 * @param request
	 * @return Map<String, String>:页面传参的集合
	 */
	public Map<String, String> getParametersFromPage(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		String varName = "default";
		String varValue = null;
		StringBuilder log = new StringBuilder("参数打印:");
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) { // 循环获取参数
			Object obj = e.nextElement();
			varName = obj.toString();
			varValue = request.getParameter(varName);
			param.put(varName, varValue);
			log.append(varName).append("=").append(varValue);
		}

		return param;
	}

	/**
	 * 处理页面传入参数
	 * 
	 * @param request
	 * @return Map<String, String>:页面传参的集合
	 */
	public Map<String, String> getOutPlat(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		String bizContent = request.getParameter("biz_content");
		String fromSource = request.getParameter("fromSource");
		param.put("biz_content", bizContent);
		param.put("fromSource", fromSource);
		return param;
	}

	/**
	 * 清除页面缓存
	 * 
	 * @param response
	 */
	protected void cleanPageCache(HttpServletResponse response) {
		// 设置response参数
		// Pragma头域用来包含实现特定的指令，最常用的是Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-
		// Control:no-cache相同。
		response.setHeader("Pragma", "no-cache");
		// 指定请求和响应遵循的缓存机制 no-cache:指示请求或响应消息不能缓存
		response.setHeader("Cache-Control", "no-cache");
		// 禁止缓存当前文档：
		response.setHeader("Expires", "0");
	}
}
