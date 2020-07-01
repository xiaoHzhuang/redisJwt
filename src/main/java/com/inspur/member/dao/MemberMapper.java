package com.inspur.member.dao;

import com.inspur.member.DO.Member;
import com.inspur.member.DO.MemberQueryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> listMembers(MemberQueryModel memberQueryModel);

    void save(Member member);

    Member getMemberById(@Param("id") String id);

    void update(Member member);

    void deleteMemberById(@Param("id") String id);
}
