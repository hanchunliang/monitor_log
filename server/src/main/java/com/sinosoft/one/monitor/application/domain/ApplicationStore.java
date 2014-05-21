package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Application;
import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.repository.ApplicationRepository;
import com.sinosoft.one.monitor.application.repository.EumUrlRepository;
import com.sinosoft.one.monitor.application.repository.MethodRepository;
import com.sinosoft.one.monitor.application.repository.UrlRepository;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建缓存application数据
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-10-16
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ApplicationStore {
    private List<Application> applications = new ArrayList<Application>();
    private Map<String,List<Url>> applicationUrlMap = new HashMap<String, List<Url>>();
    private Map<String,List<Method>> urlMethodMap = new HashMap<String, List<Method>>();
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private MethodRepository methodRepository;

    public  void load(){
        applications = applicationRepository.findAllActiveApplication();
        for (Application application:applications){
            String applicationId = application.getId();
            List<Url> urls =  urlRepository.findUrlByAppID(applicationId);
            applicationUrlMap.put(applicationId,urls);
            for (Url url:urls){
                String urlId = url.getId();
                List<Method> methods = methodRepository.selectMethodsOfUrlById(urlId);
                urlMethodMap.put(urlId,methods);
            }
        }
    }
    public void clear(){
        urlMethodMap.clear();
        applicationUrlMap.clear();
        applications.clear();
    }
    /**
     * 返回值不允许为null
     * @return
     */
    public List<Application> getApplications() {
        return applications;
    }
    /**
     * 返回值不允许为null
     * @return
     */
    public List<Url> getUrls(String applicationId) {
        List<Url> urls = applicationUrlMap.get(applicationId);
        return urls==null? Collections.EMPTY_LIST:urls;
    }
    /**
     * 返回值不允许为null
     * @return
     */
    public List<Method> getMethods(String urlId) {
        List<Method> methods = urlMethodMap.get(urlId);
        return methods==null? Collections.EMPTY_LIST:methods;
    }
}
