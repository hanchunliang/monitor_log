package com.sinosoft.one.monitor.conanal.domain;

import com.sinosoft.one.monitor.conanal.model.TableModel;
import com.sinosoft.one.monitor.conanal.repository.ConanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: king-bj
 * Date: 13-11-5
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ConanalServiceImpl implements ConanalService {

    @Autowired
    private ConanalRepository conanalRepository;


    @Override
    public List<TableModel> getCount() {
        return conanalRepository.getCount();
    }
}
