package com.inspur.member.service;

import com.inspur.common.PO.PageRequest;
import com.inspur.common.PO.PageResult;
import com.inspur.member.DO.Member;
import com.inspur.member.DO.MemberQueryModel;

public interface IMemberService {

    PageResult search(PageRequest pageRequest, MemberQueryModel memberQueryModel);

    void save(Member member);

    Member getMemberById(String id);

    void deleteMemberById(String id);
}
