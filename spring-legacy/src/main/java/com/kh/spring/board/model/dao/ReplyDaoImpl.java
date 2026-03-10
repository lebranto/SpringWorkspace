package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReplyDaoImpl implements ReplyDao{
	
	private final SqlSessionTemplate session;
	
	@Override
	public int insertReply(Reply r) {
		
		return session.insert("board.insertReply",r);
		
		// board는 mapper namespace에 있는 이름, insertReply 는 쿼리문 아이디
	}

	@Override
	public List<Reply> insertList(int boardNo) {
		
		return session.selectList("board.selectReplyList", boardNo);
	}

	@Override
	public int deleteReply(Reply r) {
		
		return session.update("board.deleteReply",r); // status 값을 바꾸기 때문에 delete를 하지 않는다
	}

}
