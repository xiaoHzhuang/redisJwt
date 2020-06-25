package com.inspur.member.controller;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.member.DO.Member;
import com.inspur.member.DO.MemberQueryModel;
import com.inspur.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/8   15:00
 * Description:
 */
@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private IMemberService memberService;

    @RequestMapping("list/search/{pageNum}/{pageSize}")
    public PageResult search(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, String cardNum, String name, Integer payType, String birthday) throws ParseException {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        Date date = null;
        if (birthday != null && !birthday.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(birthday);
        }

        MemberQueryModel memberQueryModel = new MemberQueryModel(cardNum, name, payType, date);
        return memberService.search(pageRequest, memberQueryModel);
    }

    @RequestMapping("get/{id}")
    public Member getMemberById(@PathVariable("id") String id) {
        return memberService.getMemberById(id);
    }

    @RequestMapping("detete/{id}")
    public Map<String, String> deleteMemberById(@PathVariable("id") String id) {
        Map<String, String> rs = new HashMap<String, String>();
        try {
            memberService.deleteMemberById(id);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }

    @RequestMapping("save")
    public Map<String, String> saveMember(@RequestBody Member member) {
        Map<String, String> rs = new HashMap<String, String>();
        try {
            memberService.save(member);
            rs.put("code", "1");
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("code", "0");
        }
        return rs;
    }


}