package com.inspur.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.system.page.util.PageUtils;
import com.inspur.member.DO.Member;
import com.inspur.member.DO.MemberQueryModel;
import com.inspur.member.dao.MemberMapper;
import com.inspur.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/11   9:44
 * Description:
 */
@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public PageResult search(PageRequest pageRequest, MemberQueryModel memberQueryModel) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest,memberQueryModel));
    }

    @Override
    public void save(Member member) {
        if(member.getId()!=null){
            memberMapper.update(member);
        }else{
            String id= UUID.randomUUID().toString();
            member.setId(id);
            memberMapper.save(member);
        }

    }

    @Override
    public Member getMemberById(String id) {
        return memberMapper.getMemberById(id);
    }

    @Override
    public void deleteMemberById(String id) {
        memberMapper.deleteMemberById(id);
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<Member> getPageInfo(PageRequest pageRequest, MemberQueryModel memberQueryModel) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Member> memberList = memberMapper.listMembers(memberQueryModel);
        return new PageInfo<Member>(memberList);
    }

}