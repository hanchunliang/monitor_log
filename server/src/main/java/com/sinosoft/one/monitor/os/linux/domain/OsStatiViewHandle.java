package com.sinosoft.one.monitor.os.linux.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.jhlabs.composite.HueComposite;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsCpu;
import com.sinosoft.one.monitor.os.linux.model.OsStati;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.StatiDataModel;
import com.sinosoft.one.monitor.os.linux.util.OsUtil;

@Component
public class OsStatiViewHandle {
	/**
	 *  最大值的曲线
	 * @param os
	 * @param currentTime
	 * @param type
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatCpuMaxStatiLine(List<StatiDataModel> osStatis){
		List<List<?>> data=new ArrayList<List<?>>();
		for (StatiDataModel statiDataModel:osStatis) {
			 DateTime date= new DateTime(statiDataModel.getDate());
			Long time = date.getMillis();
			Double cpuIdle = new BigDecimal(statiDataModel.getMaxValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			data.add(Lists.newArrayList(time,cpuIdle));
		}
		return data;
	}
	
	/**
	 *  平均值的曲线
	 * @param os
	 * @param currentTime
	 * @param type
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatCpuAvaStatiLine(List<StatiDataModel> osStatis){
		List<List<?>> data=new ArrayList<List<?>>();
		for (StatiDataModel statiDataModel:osStatis) {
			 DateTime date= new DateTime(statiDataModel.getDate());
			Long time = date.getMillis();
			Double cpuIdle = new BigDecimal(statiDataModel.getAvgValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			data.add(Lists.newArrayList(time,cpuIdle));
		}
		return data;
	}
	
	/**
	 * 最小值的曲线
	 * @param os
	 * @param currentTime
	 * @param type
	 * @param timespan
	 * @return
	 */
	public List<List<?>> creatCpuMinStatiLine(List<StatiDataModel> osStatis){
		List<List<?>> data=new ArrayList<List<?>>();
		for (StatiDataModel statiDataModel:osStatis) {
			 DateTime date= new DateTime(statiDataModel.getDate());
			Long time = date.getMillis();
			Double cpuIdle =  new BigDecimal(statiDataModel.getMinValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			data.add(Lists.newArrayList(time,cpuIdle));
		}
		return data;
	}
	
}

