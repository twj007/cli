package com.api.dict;

import com.common.pojo.dict.Dict;
import com.common.pojo.dict.DictDetail;

import java.util.List;

public interface DictService {

    List<Dict> getDicts(Dict dict);

    Dict getDict(Dict dict);

    Dict saveDict(Dict dict);

    DictDetail saveDictDetail(DictDetail dictDetail);

    Long importDict(List<Dict> dicts);

    Long importDictDetail(List<DictDetail> details);
}
