package com.sinosoft.one.monitor.db.oracle.model;

import java.util.ArrayList;
import java.util.List;

public class HighchartSerie {
	private String name;
	private List<Double> data = new ArrayList<Double>();

	public HighchartSerie(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getData() {
		return data;
	}
	
	public void addData(Double d) {
		data.add(d);
	}
}