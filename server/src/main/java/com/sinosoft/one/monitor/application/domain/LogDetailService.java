package com.sinosoft.one.monitor.application.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.LogDetailRepository;
import com.sinosoft.one.uiutil.Gridable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Intro:
 * User: Kylin
 * Date: 13-3-9
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class LogDetailService {

    @Autowired
    private LogDetailRepository logDetailRepository;

    /**
     * 根据logId获取请求详细信息
     * @param logId
     * @return
     */
    public List<LogDetail> getLogDetail (String logId){
        List<LogDetail> logDetails = new ArrayList<LogDetail>();
        UrlTraceLog urlTraceLog = logDetailRepository.selectUrlDetail(logId);
        if(urlTraceLog != null){
            LogDetail uLogDetail = new LogDetail();
            uLogDetail.setId(urlTraceLog.getId());
            uLogDetail.setUrlOrMethod(urlTraceLog.getUrl());
            uLogDetail.setConsumeTime(urlTraceLog.getConsumeTime());
            uLogDetail.setType("url");
            logDetails.add(uLogDetail);
        }
        List<MethodTraceLog> methodTraceLogs = logDetailRepository.selectMethodDetail(logId);
        if(methodTraceLogs != null){
            for(MethodTraceLog methodTraceLog : methodTraceLogs){
                LogDetail mLogDetail = new LogDetail();
                mLogDetail.setId(methodTraceLog.getId());
                mLogDetail.setUrlOrMethod(methodTraceLog.getMethodName());
                mLogDetail.setConsumeTime(methodTraceLog.getConsumeTime());
                mLogDetail.setType("method");
                logDetails.add(mLogDetail);
            }
        }
        return logDetails;
    }

    /**
     * 根据logId获取参数信息
     * @param logId
     * @return
     */
    public String getParamDetail(String logId) {
        UrlTraceLog urlTraceLog = logDetailRepository.selectUrlDetail(logId);
		List<ExceptionInfo> exceptionInfoList = logDetailRepository.selectExceptionInfo(logId);

        String requestParams = "";
	    if(exceptionInfoList == null||exceptionInfoList.size()==0) {
	        requestParams = urlTraceLog.getRequestParams();
	    } else {
			requestParams = exceptionInfoList.get(0).getRequestParams();
	    }
        if("{}".equals(requestParams)){
            return "{\"total\": \"0\",\"rows\": [{\"id\":\"1\",\"cell\":[\"无\",\"无\"]}]}";
        } else {
            JSONObject param = JSON.parseObject(requestParams);
            Set<String> keys = param.keySet();
            String[] keyArr = keys.toArray(new String[0]);

            JSONObject paramJson = new JSONObject();
            String[] paramArr = requestParams.substring(1,requestParams.length()-1).split(",");
            paramJson.put("total",paramArr.length);

            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < keyArr.length; i++){
                JSONObject rowData = new JSONObject();
                String key = keyArr[i];
                rowData.put("id",key);
                String value = param.getString(key);
                JSONArray cellArray = new JSONArray();
                cellArray.add(key);
                cellArray.add(value);
                rowData.put("cell",cellArray);
                jsonArray.add(rowData);
            }
            paramJson.put("rows",jsonArray);

            return paramJson.toJSONString();
        }
    }

    /**
     * 根据logId查询异常信息
     * @param logId
     * @return
     */
    public List<ExceptionInfo> getExceptionInfo(String logId){
        return logDetailRepository.selectExceptionInfo(logId);
    }

    public MethodTraceLog getMethodDetail(String methodId) {
        MethodTraceLog methodDetail = logDetailRepository.getMethodDetail(methodId);
        return methodDetail ;
    }

    /**
     * 根据alarmId查询异常信息
     * @param alarmDetailId
     * @return ExceptionInfo
     */
    public ExceptionInfo getExceptionInfoOfAlarm(String alarmDetailId) {
        return logDetailRepository.selectExceptionInfoOfAlarm(alarmDetailId);
    }
}
 
