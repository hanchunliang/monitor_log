package com.sinosoft.one.monitor.application.domain;

import com.sinosoft.one.monitor.application.model.Method;
import com.sinosoft.one.monitor.application.model.Url;
import com.sinosoft.one.monitor.application.repository.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zfb
 * Date: 13-2-27
 * Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class MethodService {

    @Autowired
    MethodRepository methodRepository;

    /**
     * 新增一个Method.
     */
    @Transactional(readOnly = false)
    public void saveMethod(Method method) {
        methodRepository.save(method);
    }

    /**
     * 删除一个Method.
     */
    @Transactional(readOnly = false)
    public void deleteMethod(Method method) {
        methodRepository.delete(method);
    }

    /**
     * 删除一个Method，通过Method的id.
     */
    @Transactional(readOnly = false)
    public void deleteMethod(String id) {
        methodRepository.delete(id);
    }

    /**
     * 查询一个Method，通过Method的id.
     */
    public Method findMethod(String id) {
        Method method = methodRepository.findMethodByid(id);
        return method;
    }

    /**
     * 查询一个Method，通过Method的名字.
     */
    public Method findMethodByMethodName(String methodName) {
        Method method = methodRepository.findByMethodName(methodName);
        return method;
    }

    /**
     * 查询所有的Method.
     */
    public List<Method> findAllMethod() {
        List<Method> urls = (List<Method>) methodRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        return urls;
    }



    /**
     * 得到Method的全类名.
     */
    public String findClassAndMethodName(Method method) {
        if (method != null) {
            String className = method.getMethodName() + "." + method.getClassName();
            return className;
        }
        return null;
    }

    /**
     * 得到URL下所有的Method.
     */
    public List<Method> findAllMethodsOfUrl(Url url) {
        return methodRepository.selectMethodsOfUrlById(url.getId());
    }

    /**
     * 更新Method.
     */
    @Transactional(readOnly = false)
    public void updateMethodWithModifyInfo(String methodId, String className, String methodName, String description, String modifierId) {
        methodRepository.updateMethod(methodId,className,methodName,description,modifierId);
    }

    /**
     * 删除URL下所有的Method.
     */
    @Transactional(readOnly = false)
    public void deleteUrlAndMethod(String urlId, String methodId) {
        //先删除中间表GE_MONITOR_URL_METHOD中的数据
        methodRepository.deleteUrlAndMethod(urlId,methodId);
        List<String> urlIds=methodRepository.selectAllUrlIdsWithMethodId(methodId);
        //如果当前method与其它url关联，那么不能删除
        if(null!=urlIds&&urlIds.size()>0){
            return;
            //如果当前method没有去其它url关联，那么删除
        }else if(null==urlIds||urlIds.size()==0){
            deleteMethod(methodId);
        }
    }

    /**
     * 批量删除URL下所有的Method(删除关联表记录).
     */
    @Transactional(readOnly = false)
    public void batchDeleteUrlAndMethod(String urlId, String[] methodIds) {
        methodRepository.batchDeleteUrlAndMethod(urlId,methodIds);
    }

    /**
     * 批量删除URL下所有的Method.
     */
    @Transactional(readOnly = false)
    public void batchDeleteMethod(String[] methodIds) {
        methodRepository.batchDeleteMethod(methodIds);
    }
}
