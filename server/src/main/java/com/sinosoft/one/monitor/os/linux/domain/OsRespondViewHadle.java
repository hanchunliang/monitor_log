package com.sinosoft.one.monitor.os.linux.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsRespondtime;

@Component
public class OsRespondViewHadle {
	@Autowired
	private OsRespondTimeService osRespondTimeService;
	
	/**
	 * 响应时间的图形MAP
	 * @param Oss
	 * @param currentTime
	 * @param interCycle
	 * @param timespan
	 */
	public Map<String,List<List<?>>> creatRespondLineData(List<Os>Oss,Date currentTime,int interCycle,int timespan){
		Map<String,List<List<?>>> osCpuUiliZation =new HashMap<String,List<List<?>>>();
		for (Os os : Oss) {
			List<List<?>> data=new ArrayList<List<?>>();
			List<OsRespondtime> osRespondtimes=osRespondTimeService.getRespondTimeByTime(os.getOsInfoId(), new DateTime(currentTime).minusHours(timespan).toDate(), currentTime);
			for (OsRespondtime osRespondtime:osRespondtimes) {
				DateTime date= new DateTime(osRespondtime.getSampleDate());
				Long time = date.getMillis();
				Double utiliZation = Double.parseDouble(osRespondtime.getRespondTime());
				data.add(Lists.newArrayList(time,utiliZation));
			}
			osCpuUiliZation.put(os.getName(), data);
		}
		return osCpuUiliZation;
	}
}
