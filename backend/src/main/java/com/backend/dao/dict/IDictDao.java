package com.backend.dao.dict;

import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/30
 **/
public interface IDictDao {
    List<Dict> getDicts(Dict dict);

    Dict getDict(Dict dict);

    List<DictDetail> getDictDetails(Long id);

    Long saveDict(Dict dict);

    Long saveDictDetail(DictDetail dictDetail);
}
