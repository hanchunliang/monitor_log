package com.sinosoft.one.monitor.application.model.viewmodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: carvin
 * Date: 13-3-10
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationUrlCountViewModel {

	private List<String> times = new ArrayList<String>();
	private Map<String, Long> urlResponseTimeMap = new HashMap<String, Long>();
	private Map<String, Integer> urlVisitNumberMap = new HashMap<String, Integer>();

	public List<String> getTimes() {
		return times;
	}

	public void addTime(String time) {
		this.times.add(time);
	}

	public Map<String, Long> getUrlResponseTimeMap() {
		return urlResponseTimeMap;
	}

	public void addUrlResponseTime(String time, Long userResponseTime) {
		urlResponseTimeMap.put(time, userResponseTime);
	}

	public Map<String, Integer> getUrlVisitNumberMap() {
		return urlVisitNumberMap;
	}

	public void addUrlVisitNumber(String time, Integer visitNumber) {
		urlVisitNumberMap.put(time, visitNumber);
	}

	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("times", times);
		jsonObject.put("urlResponseTimes", urlResponseTimeMap);
		jsonObject.put("urlVisitNumbers", urlVisitNumberMap);
		return jsonObject.toJSONString();
	}
}
