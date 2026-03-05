package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;
import com.kh.spring.security.model.vo.MemberExt;

public interface MemberService {
	public Member loginMember(Member m);
	
	public int insertMember(Member m);

	public int idCheck(String userId);

	public Member selectOne(String userId);

	public int updateMember(MemberExt loginUser);
}
