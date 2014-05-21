package com.sinosoft.one.monitor.os.linux.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsDisk;
@Component
public class OsDiskViewHandle {
	@Autowired
	private OsService osService;
	
	@Autowired
	private OsDiskService osDiskService;
	/**
	 * 构建当前Disk的grid
	 * @param osId
	 * @param currentTime
	 * @return
	 */ 
	public List<OsDisk> creatCpuResolveView(String osId,Date currentTime){
//		List<OsGridModel>osSwapViewModels=new ArrayList<OsGridModel>();
		Os os=osService.getOsBasicById(osId);
		List<OsDisk> osDisks=osDiskService.findNealyDisk(os.getOsInfoId(), currentTime, os.getIntercycleTime());
		if(osDisks.size()<1){
			 OsDisk osDisk=new OsDisk();
			 osDisk.setDiskPath("未知");
			 osDisk.setFree("未知");
			 osDisk.setFreeUtiliZation("未知");
			 osDisk.setTotal("未知");
			 osDisk.setUsed("未知");
			 osDisk.setUsedUtiliZation("未知");
			 osDisks.add(osDisk);
		}
		return osDisks;
	}
	
}
