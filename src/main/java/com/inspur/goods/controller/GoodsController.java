package com.inspur.goods.controller;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.goods.DO.Goods;
import com.inspur.goods.DO.GoodsQueryModel;
import com.inspur.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/15   16:30
 * Description:
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("save")
    public Map<String, String> saveMember(@RequestBody Goods goods) {
        Map<String, String> rs = new HashMap<String, String>(2);
        try {
            goodsService.save(goods);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }

    @RequestMapping("list/search/{pageNum}/{pageSize}")
    public PageResult search(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, String goodsname, String goodscode, String supplierid) throws ParseException {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);

        GoodsQueryModel goodsQueryModel = new GoodsQueryModel(goodsname, goodscode, supplierid);
        return goodsService.search(pageRequest, goodsQueryModel);
    }

    @RequestMapping("detete/{id}")
    public Map<String, String> deleteMemberById(@PathVariable("id") String id) {
        Map<String, String> rs = new HashMap<String, String>();
        try {
            goodsService.deleteById(id);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }

    @RequestMapping("get/{id}")
    public Goods getById(@PathVariable("id") String id) {
        return goodsService.getById(id);
    }

}