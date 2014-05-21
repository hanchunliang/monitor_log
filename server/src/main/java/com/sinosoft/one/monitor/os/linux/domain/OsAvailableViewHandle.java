package com.sinosoft.one.monitor.os.linux.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.monitor.os.linux.model.Os;
import com.sinosoft.one.monitor.os.linux.model.OsAvailable;
import com.sinosoft.one.monitor.os.linux.model.OsAvailabletemp;
import com.sinosoft.one.monitor.os.linux.model.viewmodel.OsAvailableLineModel;
import com.sinosoft.one.monitor.os.linux.util.OsTransUtil;
@Component
public class OsAvailableViewHandle {
	
	@Autowired
	private OsService osService;
	
	@Autowired
	private OsAvailableServcie osAvailableServcie;
	
	int remainder;//图形百分比进位计数 维持最大为100%
	long chufayuliang;//除法余量计算
	int listSize=0;
	/**
	 *获取可用性视图绿条数据  24小时
	 *以0 和1 之间为状态切换 计算停机和未停机时间 
	 */
	public Map<String, Object> getAvailableViewData(Date currentTime ){
		List<Os> oss = osService.getAllOs();
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String, Object>>mapList=new ArrayList<Map<String, Object>>();
		Date lastFFtime = new Date();//前24小时的整点
		Date endrecordTime = new Date();;
		for (Os os : oss) {
			Calendar c  = Calendar.getInstance();//获取当前时间的小时数 取整时点
			c.setTime(currentTime);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.add(Calendar.HOUR_OF_DAY, -24);
			lastFFtime = c.getTime();//前24小时的整点
			endrecordTime =new Date();
			List<OsAvailabletemp> osAvailabletemps=osAvailableServcie.getFFHourAvailale(os.getOsInfoId(), currentTime);
			Map<String, Object> oneOsinfomap=new HashMap<String, Object>();
			List<OsAvailableLineModel> osAvailableViewModels=new ArrayList<OsAvailableLineModel>();
			if(osAvailabletemps.size()>0){
				long nomorRun=0;//可用时间记录
				long crashtime=0;//不可用时间记录
				long timeDiffer=0;//时间差
				remainder=0;//图形百分比进位计数 维持最大为100%
				listSize=osAvailabletemps.size();//数据集合长度
				long timeLength=(currentTime.getTime()-lastFFtime.getTime())/100;//(当前时间到前24小时时间长度 )/1000/100 ：获取这个时间段中 1%的长度
				 //图形传输对象
				OsAvailableLineModel viewModel=null;//需要new的对象外放
				OsAvailabletemp lastRecord=osAvailabletemps.get(0);
				for (int i = 0; i < listSize; i++) {
					OsAvailabletemp osAvtemp=osAvailabletemps.get(i);
					if(i==0){//判断第一条数据的时间与 时间轴起点时间是否大于轮询时间
						timeDiffer=osAvtemp.getSampleDate().getTime()-osAvtemp.getIntercycleTime()*60*1000-(lastFFtime.getTime());
						if(timeDiffer>(osAvtemp.getIntercycleTime()*60*1000)){//时间差大于轮询时间则为无数据状态
							OsAvailableLineModel oneviewModel=new OsAvailableLineModel();	//创建第一个图形对象
							setOsAvailableModelPercentage(timeDiffer, timeLength, oneviewModel,"0");
							osAvailableViewModels.add(oneviewModel);
							viewModel=new OsAvailableLineModel();
						}else {
							viewModel=new OsAvailableLineModel();
							viewModel.setIndex(1);
							viewModel.setStatus("1");
							nomorRun=timeDiffer;//计入可用时间
						}
						if(listSize==1){
							setOsAvailableModelPercentage(osAvtemp.getIntercycleTime()*1000*60, timeLength, viewModel, "1");
							osAvailableViewModels.add(viewModel);
							viewModel=new OsAvailableLineModel();
							timeDiffer=currentTime.getTime()-osAvtemp.getSampleDate().getTime();
							setOsAvailableModelPercentage(timeDiffer, timeLength, viewModel, "0");
						
							osAvailableViewModels.add(viewModel);
						}
					}else {
						if(osAvtemp.getSampleDate().getTime()-lastRecord.getSampleDate().getTime()>(lastRecord.getIntercycleTime()*60*1000)+2000){
									//如果相隔时间大于轮询时间则为不可用 //则保存上一个为1的对象 创建新的对象
							crashtime=crashtime+(osAvtemp.getSampleDate().getTime()-lastRecord.getSampleDate().getTime()-lastRecord.getIntercycleTime()*60*1000);
							if(i==listSize-1){//判断是否是最后一个
								if(nomorRun>0){//如果之前记录了正常时间则存入
									setOsAvailableModelPercentage(nomorRun, timeLength, viewModel,  "1");
									osAvailableViewModels.add(viewModel);
									viewModel=new OsAvailableLineModel();
								}else{//否则本次为一次成功直接存入
									setOsAvailableModelPercentage(osAvtemp.getIntercycleTime()*60*1000, timeLength, viewModel,  "1");
									osAvailableViewModels.add(viewModel);
									viewModel=new OsAvailableLineModel();
								} 
								setOsAvailableModelPercentage(crashtime, timeLength, viewModel,"0");
								osAvailableViewModels.add(viewModel);//作为最后一个则之间的差值需要存为失败
								if(currentTime.getTime()-osAvtemp.getSampleDate().getTime()>osAvtemp.getIntercycleTime()*60*1000){//最后一个与当前时间是否有差值 如果有这为失败
									OsAvailableLineModel endViewModel=new OsAvailableLineModel();
									crashtime=currentTime.getTime()-osAvtemp.getSampleDate().getTime();
									setOsAvailableModelPercentage(crashtime, timeLength, endViewModel,"0");
									osAvailableViewModels.add(endViewModel);
								}
							}else {
								if(nomorRun>0){//2次的差值大于轮询时间 则可用时间可能为0
									setOsAvailableModelPercentage(nomorRun, timeLength, viewModel,  "1");
									osAvailableViewModels.add(viewModel);
									viewModel=new OsAvailableLineModel();//保存后再创建新的图形对象
									nomorRun=0;//可用时间归零
								}else{//2次的差值大于轮询时间 则可用时间可能为0
									setOsAvailableModelPercentage(lastRecord.getIntercycleTime()*60*1000, timeLength, viewModel,  "1");
									osAvailableViewModels.add(viewModel);
									viewModel=new OsAvailableLineModel();//保存后再创建新的图形对象
									nomorRun=0;//可用时间归零
								} 
								OsAvailableLineModel osAvailableViewModel=new OsAvailableLineModel();
								setOsAvailableModelPercentage(crashtime, timeLength, osAvailableViewModel,"0");
								osAvailableViewModels.add(osAvailableViewModel);
								crashtime=0;
							}
						}else{ 
							nomorRun+=osAvtemp.getSampleDate().getTime()-lastRecord.getSampleDate().getTime();
							if(i==listSize-1){
								setOsAvailableModelPercentage(nomorRun, timeLength, viewModel, "1");
								viewModel.setPercentage(Integer.parseInt(viewModel.getPercentage())+"");
								osAvailableViewModels.add(viewModel);
								if(currentTime.getTime()-osAvtemp.getSampleDate().getTime()>osAvtemp.getIntercycleTime()*60*1000){
									OsAvailableLineModel endViewModel=new OsAvailableLineModel();
									crashtime=currentTime.getTime()-osAvtemp.getSampleDate().getTime();
									setOsAvailableModelPercentage(crashtime, timeLength, endViewModel,"0");
									osAvailableViewModels.add(endViewModel);
								}
							} 
						}
					}
					lastRecord=osAvtemp;
					
				}
				oneOsinfomap.put("name", os.getName());
				oneOsinfomap.put("list", osAvailableViewModels);
				oneOsinfomap.put("id", os.getOsInfoId());
				mapList.add(oneOsinfomap);
			}else{
				OsAvailableLineModel endViewModel=new OsAvailableLineModel();
				endViewModel.setStatus("0");
				endViewModel.setPercentage("100");
				osAvailableViewModels.add(endViewModel);
				oneOsinfomap.put("name", os.getName());
				oneOsinfomap.put("list", osAvailableViewModels);
				oneOsinfomap.put("id", os.getOsInfoId());
				mapList.add(oneOsinfomap);
			}
		}
		map.put("beginTime", lastFFtime);
		map.put("endTime",endrecordTime);
		map.put("mapList", mapList);
		return map;
	}
	//判断和设置图形对象的百分比
	private Integer setOsAvailableModelPercentage(long timeDiffer,long timeLength ,OsAvailableLineModel osAvailableViewModel,String status){
		//差	值与1%的时间长度做除法
		if(timeDiffer%timeLength>(timeLength/2)){//如果余数大于0则图形对象中的百分比 
			osAvailableViewModel.setPercentage((timeDiffer/timeLength+1)+"");//+1为四舍五入
			remainder=remainder+1;
		}else{
			if(timeDiffer/timeLength<1){
				chufayuliang+=timeLength-timeDiffer;
				if(chufayuliang/timeLength>1&&(chufayuliang%timeLength)<timeLength){
					remainder=remainder+1;
				}
				if(status.toString().equals("0")){
					osAvailableViewModel.setPercentage(0+"");
				}else
					osAvailableViewModel.setPercentage(1+"");
			}else{
				osAvailableViewModel.setPercentage(timeDiffer/timeLength+"");
			}
		}
		osAvailableViewModel.setStatus(status);
		return remainder;
	}
	 
	
	/**
	 * 获取可用性图饼
	 * @param osinfoId
	 * @param currentDate 当前时间
	 * @param timeSpan 1 7 30天等
	 * @return
	 */
	public Map<String, Double> creatAvailablePie(String osinfoId, Date currentDate ,int timeSpan ){
		Map<String, Double> map=new HashMap<String, Double>();
		
		List<OsAvailable> osAvailables=osAvailableServcie.getAvailablesByDate(osinfoId, currentDate, timeSpan);
		long normalRun=0;
		long crashTime=0;
		for (OsAvailable osAvailable : osAvailables) {
			normalRun+=osAvailable.getNormalRun();
			crashTime+=osAvailable.getCrashTime();
		}
		String result =OsTransUtil.countUtilZation(normalRun+crashTime+"", normalRun+"");
		map.put("usable",Double.parseDouble(result) );
		map.put("unusable", 100.00- Double.parseDouble(result));
		return map;
	}
	
	
}
