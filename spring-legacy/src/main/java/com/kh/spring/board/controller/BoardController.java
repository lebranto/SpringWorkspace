package com.kh.spring.board.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final ResourceLoader resourceloader;
	private final ServletContext application; //application scope를 가진 객체
	
	/*
	 * ResourceLoader
	 *  - 스프링에서 제공하는 자원 로딩 클래스
	 *  - classpath, file 시스템, url등 다양한 경로상의 자원을
	 *   "동일한" 인터페이스로 로드하는 메서드를 제공한다.
	 * */
	
	//BoardType 전역(application) 객체 설정
	// - 어플리케이션 전역에서 사용할 수 있는 BoardType 객체 추가
	// - 서버 가동중 1회만 수행되도록 설정
	
	
	@PostConstruct // 생성자가 생성됬을 때 실행할 구문을 의미하는 어노테이션
	public void init() {
		// key = 코드 , value = 게시판이름
		Map<String, String > boardTypeMap = boardService.getBoardTypeMap();
		
		application.setAttribute("boardTypeMap", boardTypeMap);
		log.debug("boardType map = {}", boardTypeMap);
		
	}
	
	// 동적 경로 변수 사용 {} 안에 부분
	@GetMapping("/list/{boardCode}")
	public String selectList(
			@PathVariable("boardCode") String boardCode,
			/*
			 * @PathVariable
			 *  - N,P,C,M 등 동적으로 바뀌는 모든 동적 경로 변수를 바인딩 해준다.
			 *  - 선언한 동적 경로변수는 @PathVariable로 추출하여 사용할 수 있다.
			 *  - @PathVariable로 추출한 ㅈ원은 자동으로 model 영역에 추가된다.
			 * 
			 * */
			Model model, // requestScope에 집어 넣기 위해
			@RequestParam Map<String, Object> paramMap // 값을 넣어줄 공간
			) {
			/*
			 * 업무로직
			 * 1. 페이징처리
			 *   1) 현재 요청한 게시판 코드와 검색정보와 일치하는 게시글의 "총 개수" 조회
			 *   2) 게시글 갯수, 페이지 번호, 기본 파라미터들을 추출하여 페이징 정보를 가진 객체 생성
			 * 2. 현재 요청한 게시판 코드와 일치하면서, 현재 요청한 페이지에 맞는 게시글 정보를 조회
			 * 3. 게시글 정보, 페이징 정보, 검색정보를 담아서 forward
			 *  
			 * */
		// paramMap 안에 데이터 조회에 필요한 모든 정보 저장
		paramMap.put("boardCd", boardCode);
		
		List<Board> list = boardService.selectList(paramMap);
		model.addAttribute("list",list);
		
		
		return "board/boardListView";
	}
	
	
	
	
	
}
