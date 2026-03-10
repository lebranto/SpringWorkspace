package com.kh.spring.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.service.ReplyService;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.security.model.vo.MemberExt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;


/*
 * Rest(Representional state Transfer)
 * - 자원을 url 이름으로 구분하여 자원의 상태를 주고 받는것 
 *    혹은 자원의 상태를 url로 표현하는 것
 * 
 *  @RestController
 *   - @ResponseBody + @Controller의 역할을 수행하는 컨트롤러
 *   - 요청에 따른 응답데이터가 모두 값 그자체인 컨트롤러
 *   - 비동기 방식으로만 통신하는 메서드들로만 이루어진 컨트롤러에서 사용한다.
 * 
 * */

@RestController
@Slf4j
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
	
	private final ReplyService rService;
	
	// 댓글 등록 서비스
	@RequestMapping("/insert")
	public ResponseEntity<Void> insertReply(Reply r,
			Authentication auth
			){
		int userNo = ((MemberExt)auth.getPrincipal()).getUserNo();
		
		r.setReplyWriter(userNo+"");
		
		int result = rService.insertReply(r);
		
		if(result ==0 ) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.noContent().build();
		
		
	}
	
	// 댓들 목록 전체 조회 서비스
	@GetMapping("/selectList")
	public ResponseEntity<List<Reply>> selectList(int boardNo){
		List<Reply> list = rService.selectList(boardNo);
		
		if(list.isEmpty()) {
			return ResponseEntity.notFound().build(); //404 에러 
		}
		
		return ResponseEntity.ok(list);
		
		//ok 안에는 빈칸을 넣어도 되고 body에 넣을 값을 넣어도 된다.
	}
	
	@PostMapping("/delete/{replyNo}")
	public ResponseEntity<Void> deleteReply(
			@PathVariable("replyNo") int replyNo,
			Authentication auth,
			Reply r
			){
		int userNo =((MemberExt)auth.getPrincipal()).getUserNo();
		r.setReplyWriter(userNo +"");
		
		int result = rService.deleteReply(r);
		
		if(result == 0 ) {
			return ResponseEntity.badRequest().build();
		}
	
	
		return ResponseEntity.noContent().build(); 
	 }
	
	

}
