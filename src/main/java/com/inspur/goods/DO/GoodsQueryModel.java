package com.inspur.goods.DO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/15   16:48
 * Description:
 */
public class GoodsQueryModel {
    private String goodsname;

    private String goodscode;

    private String supplierid;

    public String getGoodsname() {
        return goodsname;
    }

    public GoodsQueryModel(String goodsname, String goodscode, String supplierid) {
        this.goodsname = goodsname;
        this.goodscode = goodscode;
        this.supplierid = supplierid;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodscode() {
        return goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }
}