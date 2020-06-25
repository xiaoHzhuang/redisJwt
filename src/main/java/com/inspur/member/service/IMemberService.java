package com.inspur.member.service;

import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;
import com.inspur.member.DO.Member;
import com.inspur.member.DO.MemberQueryModel;

public interface IMemberService {

    PageResult search(PageRequest pageRequest, MemberQueryModel memberQueryModel);

    void save(Member member);

    Member getMemberById(String id);

    void deleteMemberById(String id);
}
