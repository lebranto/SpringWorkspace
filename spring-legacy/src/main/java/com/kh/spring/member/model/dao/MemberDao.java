package com.kh.spring.member.model.dao;

import com.kh.spring.member.model.vo.Member;

public interface MemberDao {

	public Member loginMember(Member m);

	public int insertMember(Member m);

	public int idCheck(String userId);

	public Member selectOne(String userId);

}
