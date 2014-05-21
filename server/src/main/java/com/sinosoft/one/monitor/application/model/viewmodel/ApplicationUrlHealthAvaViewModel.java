package com.sinosoft.one.monitor.application.model.viewmodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用系统URL展示信息类.
 * User: carvin
 * Date: 13-3-9
 * Time: 上午11:41
 */
public class ApplicationUrlHealthAvaViewModel {
	private List<String> times = new ArrayList<String>();
	private Map<String, String> urlAvaViewModelMap = new HashMap<String, String>();
	private Map<String, String> urlHealthMap = new HashMap<String, String>();;

	public void addTime(String time) {
		times.add(time);
	}

	public void addUrlAva(String time, String ava) {
		urlAvaViewModelMap.put(time, ava);
	}

	public void addUrlHealth(String time, String urlHealth) {
		urlHealthMap.put(time, urlHealth);
	}

	public String toJsonString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("times", times);
		jsonObject.put("urlAvas", urlAvaViewModelMap);
		jsonObject.put("urlHealths", urlHealthMap);
		return jsonObject.toJSONString();
	}
}


