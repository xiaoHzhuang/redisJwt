package com.inspur.goods.dao;

import com.inspur.goods.DO.Goods;
import com.inspur.goods.DO.GoodsQueryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    int insert(Goods record);

    int insertSelective(Goods record);

    void update(Goods record);

    List<Goods> listGoods(GoodsQueryModel goodsQueryModel);

    void deleteById(@Param("id") String id);

    Goods getById(@Param("id") String id);
}