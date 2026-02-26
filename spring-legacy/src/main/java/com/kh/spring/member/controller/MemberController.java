package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.spring.member.model.service.MemberSeviceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller // Component Scan에 의해 읽혀져 bean객체로 등록시키게 하는 속성
public class MemberController {
	
	@Autowired //의존성 주입
	private MemberSeviceImpl mService = new MemberSeviceImpl();
	
	/*
	 * Spring DI(Dependency Injection) 
	 * - 의존성 주입 
	 * - 어플리케이션을 구성하는 객체를 개발자가 직접 생성하는게 아닌,
	 *   스프링이 생성한 객체(bean)를 주입받아서 생성하는 방식 
	 * - new 연산자를 직접 사용하지 않고, Autowired어노테이션을
	 *   주입받는다.
	 */
	
	@RequestMapping(value = "/member/login", method= RequestMethod.GET)
	public String loginMember() { //handler 메서드
		return "member/login"; // 로그인 화면으로 넘어간다. servlet-context.xml 참고
		
	}
	
	@RequestMapping(value = "/member/login", method= RequestMethod.POST)
//	public String login(HttpServletRequest req) {
//		System.out.println(req.getParameter("userId"));
//		System.out.println(req.getParameter("userPwd"));
//		
//		return "home"; // 다시 홈으로 돌아간다.
//		
//	}
	
	/*
	 * @RequestParam
	 * 	- Servlet의 request.getParameter(키값)를 대신해주는 역할을 어노테이션
	 *  - 클라이언트가 요청한 값을 대신 변환하여 바인딩 해주는 역할은 argumentResolver가 수행한다.
	 * 
	 * 
	 * */
	
	
//	public String login(@RequestParam(value="userId", defaultValue="mkm")
//		  String userId) { //userId 값을 가져와 변수(뒤의 userId)에 넣어줘라, 없다면 mkm이 기본값으로 
//		System.out.println(userId);
//		return "home";
//		
//	}
	
	// 매개변수의 이름과 "일치"하는 request의 파라미터값을 추출하여 자동 바인딩
	// 만약 일치하는 파라미터가 없다면 null값으로 바인딩

	//	public String login (String userId, String userPwd) {
//		System.out.println(userId);
//		System.out.println(userPwd);
//		return "home";
//	}
	
	
	
	/*
	 * 커맨드 객체 방식
	 * @ModerlAttribute 
	 *  - 생략이 가능하다.
	 *  - 메서드의 매개변수로 VO클래스 타입을 지정하는 경우 실행
	 *  - 요청시 전달한 name 속성과 일치하는 vo 클래스 필드의 setter 메서드를
	 *  호출하여 데이터를 바인딩 한다.
	 *  - 매개변수 클래스와 일치하는 클래스의 "기본생성자" 호출 후,
	 *   파라미터의 key값과 클새스의 필드명이 "일치" 하는 경우 setter 호출
	 * 
	 */
	
	public String login(Member m) {
		System.out.println(m);
		return "home";
	}
	
	

	
}
