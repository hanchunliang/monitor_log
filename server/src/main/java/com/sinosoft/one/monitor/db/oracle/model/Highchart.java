package com.sinosoft.one.monitor.db.oracle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class Highchart {

	/** 渲染元素ID*/
	private String renderId;
	/** x轴显示数据*/
	private List<String> categories = new ArrayList<String>();
	/** 线性数据*/
	private List<HighchartSerie> Series = new ArrayList<HighchartSerie>();

    /**
     * step
     */
    private int step = 1;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Highchart(String renderId) {
		super();
		this.renderId = renderId;
	}
	
	public String getRenderId() {
		return renderId;
	}

	public void setRenderId(String renderId) {
		this.renderId = renderId;
	}

	public List<String> getCategories() {
		return categories;
	}

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
	
	public void addCategory(String category) {
		categories.add(category);
	}

	public List<HighchartSerie> getSeries() {
		return Series;
	}

	public void addSerie(HighchartSerie Serie) {
		Series.add(Serie);
	}
}
