package com.sinosoft.one.monitor.controllers.logquery;

import com.sinosoft.one.monitor.application.domain.ApplicationService;
import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.logquery.domain.LogqueryService;
import com.sinosoft.one.monitor.logquery.model.GridRow;
import com.sinosoft.one.monitor.logquery.model.LogQueryEntity;
import com.sinosoft.one.monitor.logquery.model.RowEntity;
import com.sinosoft.one.monitor.logquery.model.UrlTraceLogEntity;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-2
 * Time: 下午10:47
 * To change this template use File | Settings | File Templates.
 */

@Path
public class LogqueryController {

    @Autowired
    private LogqueryService logqueryService;

    @Autowired
    private ApplicationService applicationService;

    @Get("logquery")
    public String getView() {
        return "logquery";
    }

    @Get("getLogInfo")
    @Post("getLogInfo")
    public Reply getLogInfo(Invocation inv) {
        //得到当前是第几页
        int pageNo = Integer.valueOf(inv.getParameter("pageNo"));
        //得到每页显示多少条
        int rowNum = Integer.valueOf(inv.getParameter("rowNum"));
        //应用名称ID
        String appId = inv.getParameter("appId");
        if(null == appId) {
            appId = "";
        }
        //地区名称
        String areaCode = inv.getParameter("areaName");
        if(null == areaCode) {
            areaCode = "";
        }
        //用户名
        String userName = inv.getParameter("userName");
        if(null == userName) {
            userName = "";
        } else {
            userName = "%" + userName + "%";
        }
        //ip
        String userIp = inv.getParameter("userIp");
        if(null == userIp) {
            userIp = "";
        } else {
            userIp = "%" + userIp + "%";
        }
        //开始时间
        String fromTime = inv.getParameter("fromTime");
        if(null == fromTime) {
            fromTime = "";
        }
        //结束时间
        String endTime = inv.getParameter("endTime");
        if(null == endTime) {
            endTime = "";
        }
        List<UrlTraceLogEntity> urlTraceLogEntityList = new ArrayList<UrlTraceLogEntity>();
        urlTraceLogEntityList = logqueryService.getUrlTraceLogInfo(appId, userName, userIp, fromTime, endTime, areaCode);
        //得到一共有多少条记录
        int totalRecord = urlTraceLogEntityList.size();
        GridRow gridRow = new GridRow();
        gridRow.setTotal(totalRecord);
        //把需要的数据装入将要返回给前台的list
        List<Object> rowEntityList = new ArrayList<Object>();
        if(totalRecord <= (pageNo+1)*rowNum) {
            //数据库查出的数据小于grid要展示的数据
            for(int i = pageNo*rowNum; i < totalRecord; i++) {
                RowEntity rowEntity = new RowEntity();
                List<String> strList = new ArrayList<String>();
                rowEntity.setId(urlTraceLogEntityList.get(i).getId());
                strList.add(logqueryService.findOptInfoById(urlTraceLogEntityList.get(i).getUrlId()));
                strList.add(logqueryService.findAppNameById(urlTraceLogEntityList.get(i).getApplicationId()));
                strList.add(urlTraceLogEntityList.get(i).getUsername());
                strList.add(urlTraceLogEntityList.get(i).getUserIp());
                strList.add(urlTraceLogEntityList.get(i).getRecordTime().toString());
                rowEntity.setCell(strList);
                rowEntityList.add(rowEntity);
            }
        } else {
            //数据库查出的数据大于grid要展示的数据
            for(int i = pageNo*rowNum; i < (pageNo+1)*rowNum; i++) {
                RowEntity rowEntity = new RowEntity();
                List<String> strList = new ArrayList<String>();
                rowEntity.setId(urlTraceLogEntityList.get(i).getId());
                strList.add(logqueryService.findOptInfoById(urlTraceLogEntityList.get(i).getUrlId()));
                strList.add(logqueryService.findAppNameById(urlTraceLogEntityList.get(i).getApplicationId()));
                strList.add(urlTraceLogEntityList.get(i).getUsername());
                strList.add(urlTraceLogEntityList.get(i).getUserIp());
                strList.add(urlTraceLogEntityList.get(i).getRecordTime().toString());
                rowEntity.setCell(strList);
                rowEntityList.add(rowEntity);
            }
        }
        gridRow.setRows(rowEntityList);
        //根据查出的应用名称id找到对应的应用名称
        //根据查出的url_id找到对应的操作信息
        return Replys.with(gridRow).as(Json.class);
    }

    @Post("appList")
    @Get("appList")
    public Reply appList(Invocation inv) {
        int pageNo = Integer.valueOf(inv.getParameter("pageNo"));
        int rowNum = Integer.valueOf(inv.getParameter("rowNum"));
        //查出所有的应用信息
        List<Application> applicationList = applicationService.findAllApplication();
        int totalSize = applicationList.size();
        GridRow gridRow = new GridRow();
        gridRow.setTotal(totalSize);
        //把需要的数据装入将要返回给前台的list
        List<Object> rowEntityList = new ArrayList<Object>();
        if(totalSize <= (pageNo+1)*rowNum) {
            //数据库查出的数据小于grid要展示的数据
            for(int i = pageNo*rowNum; i < totalSize; i++) {
                RowEntity rowEntity = new RowEntity();
                List<String> strList = new ArrayList<String>();
                rowEntity.setId(applicationList.get(i).getId());
                strList.add(applicationList.get(i).getApplicationName());
                strList.add(applicationList.get(i).getApplicationIp());
                strList.add(applicationList.get(i).getApplicationPort());
                strList.add(applicationList.get(i).getCreateTime().toString());
                strList.add(applicationList.get(i).getCnName());
                rowEntity.setCell(strList);
                rowEntityList.add(rowEntity);
            }
        } else {
            //数据库查出的数据大于grid要展示的数据
            for(int i = pageNo*rowNum; i < (pageNo+1)*rowNum; i++) {
                RowEntity rowEntity = new RowEntity();
                List<String> strList = new ArrayList<String>();
                rowEntity.setId(applicationList.get(i).getId());
                strList.add(applicationList.get(i).getApplicationName());
                strList.add(applicationList.get(i).getApplicationIp());
                strList.add(applicationList.get(i).getApplicationPort());
                strList.add(applicationList.get(i).getCreateTime().toString());
                strList.add(applicationList.get(i).getCnName());
                rowEntity.setCell(strList);
                rowEntityList.add(rowEntity);
            }
        }
        gridRow.setRows(rowEntityList);
        return Replys.with(gridRow).as(Json.class);
    }

}
