package com.api.dict;

import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;

import java.util.List;

public interface DictService {

    List<Dict> getDicts(Dict dict);

    Dict getDict(Dict dict);

    Dict saveDict(Dict dict);

    DictDetail saveDictDetail(DictDetail dictDetail);

    Long updateDict(Dict dict);

    Long updateDictDetail(DictDetail detail);

    Long deleteDict(Dict dict);

    Long deleteDictDetail(DictDetail detail);

    Long importDict(List<Dict> dicts);

}
