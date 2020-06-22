package com.inspur.member.DO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/12   17:25
 * Description:
 */
public class MemberQueryModel {
    private String id;
    private String memberName;
    private Integer payType;
    private Date memberBirthday;

    public MemberQueryModel() {
    }

    public MemberQueryModel(String id, String memberName, Integer payType, Date memberBirthday) {
        this.id = id;
        this.memberName = memberName;
        this.payType = payType;
        this.memberBirthday = memberBirthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(Date memberBirthday) {
        this.memberBirthday = memberBirthday;
    }
}