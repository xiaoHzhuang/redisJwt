package com.inspur.goods.service;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.goods.DO.Goods;
import com.inspur.goods.DO.GoodsQueryModel;

public interface IGoodsService {
    void save(Goods goods);

    PageResult search(PageRequest pageRequest, GoodsQueryModel goodsQueryModel);

    void deleteById(String id);

    Goods getById(String id);
}
