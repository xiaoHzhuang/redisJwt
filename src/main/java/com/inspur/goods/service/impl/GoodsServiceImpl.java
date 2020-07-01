package com.inspur.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.system.page.util.PageUtils;
import com.inspur.goods.DO.Goods;
import com.inspur.goods.DO.GoodsQueryModel;
import com.inspur.goods.dao.GoodsMapper;
import com.inspur.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/15   16:31
 * Description:
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void save(Goods goods) {
        if(goods.getId()==null || goods.getId().equals("")){
            goods.setId(UUID.randomUUID().toString());
            goodsMapper.insertSelective(goods);
        }else{
            goodsMapper.update(goods);
        }
    }

    @Override
    public PageResult search(PageRequest pageRequest, GoodsQueryModel goodsQueryModel) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList =goodsMapper.listGoods(goodsQueryModel);
        return PageUtils.getPageResult(pageRequest,new PageInfo<Goods>(goodsList));
    }

    @Override
    public void deleteById(String id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public Goods getById(String id) {
        return goodsMapper.getById(id);
    }
}