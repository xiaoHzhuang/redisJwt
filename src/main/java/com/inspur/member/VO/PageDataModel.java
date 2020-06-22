package com.inspur.member.VO;

import com.inspur.member.DO.Member;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/12   13:47
 * Description:
 */
public class PageDataModel {
    private int total;
    private List<Member> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Member> getRows() {
        return rows;
    }

    public void setRows(List<Member> rows) {
        this.rows = rows;
    }
}