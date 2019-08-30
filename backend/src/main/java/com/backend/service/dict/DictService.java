package com.backend.service.dict;

import com.backend.dao.dict.IDictDao;
import com.common.annotation.DataSource;
import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/30
 **/
@Service(timeout = 10000, filter = "RpcFilter", interfaceClass = com.api.dict.DictService.class)
public class DictService implements com.api.dict.DictService {

    @Autowired
    private IDictDao dictDao;

    @Override
    @DataSource
    @Transactional(readOnly = true)
    public List<Dict> getDicts(Dict dict) {
        return dictDao.getDicts(dict);
    }

    @Override
    @DataSource
    public Dict getDict(Dict dict) {
        return dictDao.getDict(dict);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @DataSource
    public Dict saveDict(Dict dict) {
        Long num = dictDao.saveDict(dict);
        if(num == 01L){
            return dict;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @DataSource
    public DictDetail saveDictDetail(DictDetail dictDetail) {
        Long num = dictDao.saveDictDetail(dictDetail);
        if(num == 01L){
            return dictDetail;
        }
        return null;
    }

    @Override
    public Long importDict(List<Dict> dicts) {
        return null;
    }

    @Override
    public Long importDictDetail(List<DictDetail> details) {
        return null;
    }


}
